package nz.ac.auckland.se281;

public abstract class Review {
  private String reviewText;
  private int rating;
  private String reviewerName;
  private String reviewId;

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
