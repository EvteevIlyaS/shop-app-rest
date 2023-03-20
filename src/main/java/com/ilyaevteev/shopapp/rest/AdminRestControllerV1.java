package com.ilyaevteev.shopapp.rest;

import com.ilyaevteev.shopapp.dto.*;
import com.ilyaevteev.shopapp.dto.auth.UserDto;
import com.ilyaevteev.shopapp.model.*;
import com.ilyaevteev.shopapp.model.auth.Role;
import com.ilyaevteev.shopapp.model.auth.User;
import com.ilyaevteev.shopapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminRestControllerV1 {
    private final OrganizationCreateQueryService organizationCreateQueryService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final RoleService roleService;
    private final ProductService productService;
    private final OrganizationService organizationService;
    private final DiscountService discountService;

    @Autowired
    public AdminRestControllerV1(OrganizationCreateQueryService organizationCreateQueryService, NotificationService notificationService, UserService userService, RoleService roleService, ProductService productService,
                                 OrganizationService organizationService, DiscountService discountService) {
        this.organizationCreateQueryService = organizationCreateQueryService;
        this.notificationService = notificationService;
        this.userService = userService;
        this.roleService = roleService;
        this.productService = productService;
        this.organizationService = organizationService;
        this.discountService = discountService;
    }

    @PostMapping(value = "create-update/product")
    public ResponseEntity createUpdateProduct(@RequestBody ProductDto productDto) {
        Product product;
        Long productId = productDto.getId();

        if (productId == null) {
            product = new Product();
        } else {
            product = productService.findById(productDto.getId());

        }

        try {
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setCountStock(productDto.getCountStock());
            product.setKeywords(productDto.getKeywords());
            product.setCharacteristicTable(productDto.getCharacteristicTable());

            product.setOrganization(organizationService
                    .findById(productDto
                            .getOrganization()));

            Long discountId = productDto.getDiscount();
            if (discountId != null) {
                product.setDiscount(discountService
                        .findById(discountId));
            }

            if (productId == null) {
                productService.saveProduct(product);
            }
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "Invalid product data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        productDto.setId(product.getId());
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

//    @DeleteMapping(value = "delete/product")
//    public ResponseEntity deleteProductByName(@RequestBody ProductDto productDto) {
//        try {
//            productService.deleteProduct(productDto.getName());
//        } catch (Exception e) {
//            Map<Object, Object> response = new HashMap<>();
//            response.put("Error", "Invalid product name");
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping(value = "create/organization")
    public ResponseEntity createOrganization(@RequestBody OrganizationDto organizationDto) {
        Organization organization = new Organization();

        try {
            organization.setName(organizationDto.getName());
            organization.setDescription(organizationDto.getDescription());
            organization.setLogo(organizationDto.getLogo());

            organization.setUser(userService
                    .findById(organizationDto
                            .getUser()));

            organizationService.saveOrganization(organization);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "Invalid organization data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        organizationDto.setId(organization.getId());
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);

    }

    @PostMapping(value = "create-update/discount")
    public ResponseEntity createUpdateDiscount(@RequestBody DiscountDto discountDto) {
        Discount discount;

        if (discountDto.getId() != null) {
            discount = discountService.findById(discountDto.getId());
        } else {
            discount = new Discount();
        }

        try {
            discount.setAmountPercentage(discountDto.getAmountPercentage());
            discount.setDurationUpTo(discountDto.getDurationUpTo());

            discountService.saveOrUpdateDiscount(discount);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "Invalid discount data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        discountDto.setId(discount.getId());
        return new ResponseEntity<>(discountDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/discount")
    public ResponseEntity deleteDiscountById(@RequestBody DiscountDto discountDto) {
        try {
            discountService.deleteDiscount(discountDto.getId());
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "Invalid discount id or there are products using this discount");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "history")
    public ResponseEntity<List<HistoryDto>> getUserHistoryByUsername(UserDto userDto) {
        User user = userService.findByUsername(userDto.getUsername());
        List<History> userHistory = user.getHistory();

        return new ResponseEntity<>(userHistory.stream().map(HistoryDto::fromHistory).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping(value = "set-balance")
    public ResponseEntity setUserBalanceByUsername(UserDto userDto) {
        User user = userService.findByUsername(userDto.getUsername());
        user.setBalance(user.getBalance());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value = "users/frozen/{id}")
    @Transactional
    public ResponseEntity<UserDto> frozenUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        user.getRoles().removeIf(element -> element.getName().equals("ROLE_USER"));
        Role role = roleService.findByName("ROLE_USER");
        role.getUsers().removeIf(element -> element.getId().equals(id));

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "users/delete/{id}")
    @Transactional
    public ResponseEntity deleteUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        try {
            for (Role role : user.getRoles()) {
                role.getUsers().removeIf(element -> element.getId().equals(id));
            }
            userService.delete(id);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "There are objects using this user's id as a foreign key");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "send/notification")
    public ResponseEntity sendNotificationById(@RequestBody NotificationDto notificationDto) {
        User user = userService.findById(notificationDto.getId());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setHeader(notificationDto.getHeader());
        notification.setText(notificationDto.getText());
        notification.setUser(user);

        notificationService.sendNotification(notification);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "register-org/{id}")
    @Transactional
    public ResponseEntity acceptRegisterOrganizationById(@PathVariable(name = "id") Long id) {
        OrganizationCreateQuery organizationCreateQuery = organizationCreateQueryService.findById(id);
        if (organizationCreateQuery == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        User user = organizationCreateQuery.getUser();
        String nameOrg = organizationCreateQuery.getName();

        if (organizationService.findByName(nameOrg) != null) {
            Map<Object, Object> response = new HashMap<>();
            response.put("Error", "An organization with the same name already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Organization organization = new Organization();
        organization.setLogo(organizationCreateQuery.getLogo());
        organization.setUser(user);
        organization.setDescription(organizationCreateQuery.getDescription());
        organization.setName(nameOrg);

        organizationService.saveOrganization(organization);
        organizationCreateQueryService.deleteById(id);
        user.getRoles().add(roleService.findByName("ROLE_ORG"));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "orgs/frozen/{id}")
    @Transactional
    public ResponseEntity<OrganizationDto> frozenOrganizationById(@PathVariable(name = "id") Long id) {
        Organization organization = organizationService.findById(id);

        if (organization == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        User owner = organization.getUser();
        if (owner.getOrganizations().size() == 0) {
            owner.getRoles().removeIf(element -> element.getName().equals("ROLE_ORG"));
            Role role = roleService.findByName("ROLE_ORG");
            role.getUsers().removeIf(element -> element.getId().equals(owner.getId()));
        }


        organization.setUser(null);
        OrganizationDto result = OrganizationDto.fromOrganization(organization);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
