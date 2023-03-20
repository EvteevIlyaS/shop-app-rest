package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.Rating;
import com.ilyaevteev.shopapp.repository.RatingRepository;
import com.ilyaevteev.shopapp.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepository;
    @Override
    @Transactional
    public void saveRating(Rating rating) {
        ratingRepository.saveRating(rating);
    }
}
