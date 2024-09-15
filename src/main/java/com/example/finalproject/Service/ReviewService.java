package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Review;

import com.example.finalproject.Repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Review review) {
        reviewRepository.save(review);
    }
    public void updateReview(Integer id,Review review) {
        Review review1=reviewRepository.findReviewById(id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        review1.setComment(review.getComment());
        review1.setRating(review.getRating());
        review1.setDateCreated(review.getDateCreated());
        reviewRepository.save(review1);
    }
    public void deleteReview(Integer id) {
        Review review1=reviewRepository.findReviewById(id);
        if(review1==null){
            throw new ApiException("Review not found");
        }
        reviewRepository.delete(review1);
    }


}
