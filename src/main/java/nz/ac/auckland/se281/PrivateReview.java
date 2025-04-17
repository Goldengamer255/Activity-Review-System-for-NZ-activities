package nz.ac.auckland.se281;

public class PrivateReview extends Review {
  private String email;
  private boolean followUpRequested;
  private String operatorResponse;

  public PrivateReview(
      String reviewerName, String email, int rating, String comments, boolean followUpRequested) {
    super(comments, rating, reviewerName, null); // Pass null for reviewId if it's not needed
    this.email = email;
    this.followUpRequested = followUpRequested;
    this.operatorResponse = null;
  }

  public void respond(String response) {
    this.operatorResponse = response;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getEmail() {
    return email;
  }

  public String getOperatorResponse() {
    return operatorResponse;
  }

  public boolean isFollowUpRequested() {
    return followUpRequested;
  }

  @Override
  public void printReviewDetails() { // Print details specific to PrivateReview
    System.out.println("Private Review by " + getReviewerName() + ":");
    System.out.println("Email: " + email);
    System.out.println("Rating: " + getRating());
    System.out.println("Comments: " + getReviewText());
    if (followUpRequested) {
      if (operatorResponse != null) { // If there's a response from the operator
        System.out.println("Operator Response: " + operatorResponse);
      } else { // If no response yet
        System.out.println("A reply is needed.");
      }
    } else { // If no follow-up is requested
      System.out.println("This review is resolved.");
    }
  }
}
