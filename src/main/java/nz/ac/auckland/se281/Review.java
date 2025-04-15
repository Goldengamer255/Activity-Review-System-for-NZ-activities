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
}
