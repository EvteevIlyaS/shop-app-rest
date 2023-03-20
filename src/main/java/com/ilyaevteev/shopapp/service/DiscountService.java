package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.Discount;

public interface DiscountService {
    Discount findById(Long id);

    void saveOrUpdateDiscount(Discount discount);

    void deleteDiscount(Long id);


}
