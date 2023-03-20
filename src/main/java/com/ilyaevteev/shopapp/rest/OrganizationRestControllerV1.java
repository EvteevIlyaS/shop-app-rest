package com.ilyaevteev.shopapp.rest;

import com.ilyaevteev.shopapp.dto.ProductDto;
import com.ilyaevteev.shopapp.model.Organization;
import com.ilyaevteev.shopapp.model.Product;
import com.ilyaevteev.shopapp.service.DiscountService;
import com.ilyaevteev.shopapp.service.OrganizationService;
import com.ilyaevteev.shopapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/org/")
public class OrganizationRestControllerV1 {
    private final OrganizationService organizationService;
    private final DiscountService discountService;
    private final ProductService productService;

    @Autowired
    public OrganizationRestControllerV1(OrganizationService organizationService, DiscountService discountService, ProductService productService) {
        this.organizationService = organizationService;
        this.discountService = discountService;
        this.productService = productService;
    }

    @PostMapping(value = "create/product")
    public ResponseEntity createProduct(@RequestBody ProductDto productDto, Authentication authentication) {
        Product product = new Product();
        String username = authentication.getName();
        Map<Object, Object> response = new HashMap<>();

        try {
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setCountStock(productDto.getCountStock());
            product.setKeywords(productDto.getKeywords());
            product.setCharacteristicTable(productDto.getCharacteristicTable());

            Organization organization = organizationService.findById(productDto.getOrganization());
            if (!organization.getUser().getUsername().equals(username)) {
                response.put("Error", "The user is not the owner of this organization");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            product.setOrganization(organization);

            Long discountId = productDto.getDiscount();
            if (discountId != null) {
                product.setDiscount(discountService.findById(discountId));
            }

            if (isValidProduct(product)) productService.saveProduct(product);

        } catch (Exception e) {
            response.put("Error", "Invalid product data");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        productDto.setId(product.getId());
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    private boolean isValidProduct(Product product) {
        // Is valid product?
        // Product moderation...
        return true;
    }
}
