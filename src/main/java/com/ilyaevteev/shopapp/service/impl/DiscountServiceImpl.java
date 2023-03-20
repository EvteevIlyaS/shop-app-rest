package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.Discount;
import com.ilyaevteev.shopapp.repository.DiscountRepository;
import com.ilyaevteev.shopapp.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    DiscountRepository discountRepository;

    @Override
    @Transactional
    public Discount findById(Long id) {
        return discountRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveOrUpdateDiscount(Discount discount) {
        discountRepository.saveDiscount(discount);
    }

    @Override
    @Transactional
    public void deleteDiscount(Long id) {
        discountRepository.deleteDiscount(id);
    }
}
