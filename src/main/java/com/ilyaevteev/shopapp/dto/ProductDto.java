package com.ilyaevteev.shopapp.dto;

import com.ilyaevteev.shopapp.model.Product;
import lombok.Data;

@Data
public class ProductDto {
    Long id;

    private String name;

    private String description;

    private int price;

    private int countStock;

    private String keywords;

    private String characteristicTable;

    private Long organization;

    private Long discount;

    public static ProductDto fromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCountStock(product.getCountStock());
        productDto.setKeywords(product.getKeywords());
        productDto.setCharacteristicTable(product.getCharacteristicTable());
        productDto.setOrganization(product.getOrganization().getId());
        productDto.setDiscount(product.getDiscount().getId());

        return productDto;
    }
}
