package nz.ac.auckland.se281;

public class PublicReview extends Review {
  private boolean isAnonymous;
  private boolean isEndorsed;

  PublicReview(
      String reviewText, int rating, String reviewerName, String reviewId, boolean isAnonymous) {
    super(reviewText, rating, reviewerName, reviewId);
    this.isAnonymous = isAnonymous;
    this.isEndorsed = false;
  }

  public void endorse() {
    this.isEndorsed = true;
  }

  public boolean isEndorsed() {
    return isEndorsed;
  }

  public void setEndorsed(boolean endorsed) {
    this.isEndorsed = endorsed;
  }

  public boolean isAnonymous() {
    return isAnonymous;
  }

  @Override
  public void printReviewDetails() {
    String name = isAnonymous ? "Anonymous" : getReviewerName();
    String endorsement = isEndorsed ? " (Endorsed)" : "";
    System.out.println(name + ": " + getReviewText() + " - Rating: " + getRating() + endorsement);
  }
}
