package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.Review;
import com.ilyaevteev.shopapp.repository.ReviewRepository;
import com.ilyaevteev.shopapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Override
    @Transactional
    public void saveReview(Review review) {
        reviewRepository.saveReview(review);
    }
}
