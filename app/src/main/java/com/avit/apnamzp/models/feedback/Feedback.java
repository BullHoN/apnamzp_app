package com.avit.apnamzp.models.feedback;

import com.avit.apnamzp.models.ReviewData;

public class Feedback {
    private ReviewData foodReview;
    private ReviewData deliveySathiReview;
    private ReviewData apnaReview;

    public Feedback(ReviewData foodReview, ReviewData deliveySathiReview, ReviewData apnaReview) {
        this.foodReview = foodReview;
        this.deliveySathiReview = deliveySathiReview;
        this.apnaReview = apnaReview;
    }

    public ReviewData getFoodReview() {
        return foodReview;
    }

    public ReviewData getDeliveySathiReview() {
        return deliveySathiReview;
    }

    public ReviewData getApnaReview() {
        return apnaReview;
    }
}
