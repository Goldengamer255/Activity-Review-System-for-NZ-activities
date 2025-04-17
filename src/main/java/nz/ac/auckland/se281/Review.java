package nz.ac.auckland.se281;

public abstract class Review {
  protected String reviewText;
  protected int rating;
  protected String reviewerName;
  protected String reviewId;

  public Review(String reviewText, int rating, String reviewerName, String reviewId) {
    this.reviewText = reviewText;
    this.rating = rating;
    this.reviewerName = reviewerName;
    this.reviewId = reviewId;
  }

  public String getReviewText() {
    return reviewText;
  }

  public int getRating() {
    return rating;
  }

  public String getReviewId() {
    return reviewId;
  }

  public String getReviewerName() {
    return reviewerName;
  }

  public abstract void printReviewDetails();
}
