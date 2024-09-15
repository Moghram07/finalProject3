package com.example.finalproject.Controller;

import com.example.finalproject.Model.Review;
import com.example.finalproject.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("/get")
    public ResponseEntity getAllReviews() {
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }

    @PostMapping("/add")
    public ResponseEntity addReview(@Valid @RequestBody Review review) {
        reviewService.addReview(review);
        return ResponseEntity.status(200).body("Review added ");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@PathVariable Integer id,@Valid @RequestBody Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body("Review updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body("Review deleted");
    }

}