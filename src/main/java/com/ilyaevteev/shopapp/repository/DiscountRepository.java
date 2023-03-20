package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.Discount;

public interface DiscountRepository {
    Discount findById(Long id);

    public void saveDiscount(Discount discount);

    public void deleteDiscount(Long id);

}
