package nz.ac.auckland.se281;

public class ExpertReview extends Review {
  private String recommendation;

  // Constructor matching the provided arguments
  public ExpertReview(
      String reviewText, int rating, String reviewerName, String reviewId, String recommendation) {
    super(reviewText, rating, reviewerName, reviewId); // Call the superclass constructor
    this.recommendation = recommendation;
  }

  public String getRecommendation() {
    return recommendation;
  }

  @Override
  public void printReviewDetails() {
    System.out.println("Expert Review by " + getReviewerName() + ":");
    System.out.println("Rating: " + getRating());
    System.out.println("Comments: " + getReviewText());
    System.out.println("Recommendation: " + recommendation);
  }
}
