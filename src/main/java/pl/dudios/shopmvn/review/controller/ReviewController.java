package pl.dudios.shopmvn.review.controller;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.dudios.shopmvn.common.model.Review;
import pl.dudios.shopmvn.review.service.ReviewService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public Review addReview(@RequestBody @Valid ReviewDto review) {
        return reviewService.addReview(
                Review.builder()
                        .authorName(cleanContent(review.authorName()))
                        .content(cleanContent(review.content()))
                        .productId(review.productId())
                        .build()
        );
    }

    private String cleanContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }
}
