package com.ilyaevteev.shopapp.rest;

import com.ilyaevteev.shopapp.dto.*;
import com.ilyaevteev.shopapp.model.*;
import com.ilyaevteev.shopapp.model.auth.User;
import com.ilyaevteev.shopapp.service.*;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/user/")
public class UserRestControllerV1 {
    private final OrganizationCreateQueryService organizationCreateQueryService;
    private final UserService userService;
    private final ProductService productService;
    private final HistoryService historyService;
    private final ReviewService reviewService;
    private final RatingService ratingService;

    private final int COMMISSION = 5;

    @Autowired
    public UserRestControllerV1(OrganizationCreateQueryService organizationCreateQueryService, UserService userService, ProductService productService, HistoryService historyService, ReviewService reviewService, RatingService ratingService) {
        this.organizationCreateQueryService = organizationCreateQueryService;
        this.userService = userService;
        this.productService = productService;
        this.historyService = historyService;
        this.reviewService = reviewService;
        this.ratingService = ratingService;
    }


    @PutMapping(value = "buy-product")
    @Transactional
    public ResponseEntity buyProductByName(@RequestBody ProductDto productDto, Authentication authentication) {
        String productName = productDto.getName();
        String username = authentication.getName();

        Map<Object, Object> response = new HashMap<>();
        try {
            Product product = productService.findByName(productName);
            Discount discount = product.getDiscount();

            if (product.getCountStock() == 0) {
                response.put("Error", "Out of stock");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int price = product.getPrice();
            if (discount != null & discount.getDurationUpTo().getTime() - new Date().getTime() >= 0) {
                price = price * (100 - discount.getAmountPercentage()) / 100;
            }

            User user = userService.findByUsername(username);
            Long balance = user.getBalance();
            if (balance < price) {
                response.put("Error", "Not enough money");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            product.setCountStock(product.getCountStock() - 1);
            user.setBalance(balance - price);

            User orgOwner = product.getOrganization().getUser();
            orgOwner.setBalance(orgOwner.getBalance() + (long) price * (100 - COMMISSION) / 100);

            History history = new History();
            history.setUser(user);
            history.setDate(new Date());
            history.setName(productName);
            historyService.saveHistory(history);

            HistoryDto historyDto = HistoryDto.fromHistory(history);
            return new ResponseEntity<>(historyDto, HttpStatus.OK);

        } catch (Exception e) {
            response.put("Error", "Invalid data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "add/review")
    public ResponseEntity addReview(@RequestBody ReviewDto reviewDto, Authentication authentication) {
        String productName = reviewDto.getProduct();
        String username = authentication.getName();
        Map<Object, Object> response = new HashMap<>();

        if (!checkValidity(productName, username, response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Review review = new Review();
        review.setProduct(productService.findByName(productName));
        review.setReview(reviewDto.getReview());
        reviewService.saveReview(review);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "add/rating")
    public ResponseEntity addRating(@RequestBody RatingDto ratingDto, Authentication authentication) {
        String productName = ratingDto.getProduct();
        String username = authentication.getName();
        int ratingVal = ratingDto.getRating();
        Map<Object, Object> response = new HashMap<>();

        if (ratingVal < 0 | ratingVal > 5) {
            response.put("Error", "Invalid data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!checkValidity(productName, username, response)) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Rating rating = new Rating();
        rating.setProduct(productService.findByName(productName));
        rating.setRating(ratingVal);
        ratingService.saveRating(rating);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean checkValidity(String productName, String username, Map<Object, Object> response) {
        if (productService.findByName(productName) == null) {
            response.put("Error", "Invalid data");
            return false;
        }

        User user = userService.findByUsername(username);
        List<History> userHistory = user.getHistory();
        if (userHistory.stream().noneMatch(history -> Objects.equals(history.getName(), productName))) {
            response.put("Error", "The product has never been purchased by the user");
            return false;
        }

        return true;
    }

    @GetMapping(value = "history")
    public ResponseEntity<List<HistoryDto>> getUserHistory(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<History> userHistory = user.getHistory();

        return new ResponseEntity<>(userHistory.stream().map(HistoryDto::fromHistory).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(value = "return-product")
    @Transactional
    public ResponseEntity returnProductById(@RequestBody HistoryDto historyDto, Authentication authentication) {
        Long historyId = historyDto.getId();
        User user = userService.findByUsername(authentication.getName());

        List<History> userHistory = user.getHistory();
        Map<Object, Object> response = new HashMap<>();


        if (userHistory.stream().noneMatch(history -> Objects.equals(history.getId(), historyId))) {
            response.put("Error", "Wrong purchase id");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        History history = historyService.findById(historyId);
        if (new Period(new DateTime(history.getDate()), new DateTime(new Date())).getHours() > 24) {
            response.put("Error", "Too late to return");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Product product = productService.findByName(history.getName());
        int price = product.getPrice() * (100 - product.getDiscount().getAmountPercentage()) / 100;

        user.setBalance(user.getBalance() + price);
        product.setCountStock(product.getCountStock() + 1);

        User orgOwner = product.getOrganization().getUser();
        orgOwner.setBalance(orgOwner.getBalance() - (long) price * (100 - COMMISSION) / 100);

        historyService.delete(historyId);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "notification")
    public ResponseEntity<List<NotificationDto>> getUserNotifications(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        List<Notification> userNotifications = user.getNotifications();

        return new ResponseEntity<>(userNotifications.stream().map(NotificationDto::fromNotification).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(value = "register-org")
    public ResponseEntity registerOrganization(@RequestBody OrganizationDto organizationDto, Authentication authentication) {
        OrganizationCreateQuery organizationCreateQuery = new OrganizationCreateQuery();

        try {
            organizationCreateQuery.setName(organizationDto.getName());
            organizationCreateQuery.setDescription(organizationDto.getDescription());
            organizationCreateQuery.setLogo(organizationDto.getLogo());

            organizationCreateQuery.setUser(userService.findByUsername(authentication.getName()));

            organizationCreateQueryService.saveOrganizationCreateQuery(organizationCreateQuery);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "Invalid organization-create-query data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        organizationDto.setId(organizationCreateQuery.getId());
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
    }

    @GetMapping(value = "products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getAllNotFrozenDeleted();

        return new ResponseEntity<>(products.stream().map(ProductDto::fromProduct).collect(Collectors.toList()), HttpStatus.OK);
    }
}
