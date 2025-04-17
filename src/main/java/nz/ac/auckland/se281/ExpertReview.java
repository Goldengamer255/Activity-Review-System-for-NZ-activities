package nz.ac.auckland.se281;

import java.util.ArrayList;

public class ExpertReview extends Review {
  private String recommendation; // Recommendation from the expert
  private ArrayList<String> images; // List to hold image paths or URLs

  // Constructor matching the provided arguments
  public ExpertReview(
      String reviewText, int rating, String reviewerName, String reviewId, String recommendation) {
    super(reviewText, rating, reviewerName, reviewId); // Call the superclass constructor
    this.recommendation = recommendation;
    this.images = new ArrayList<>(); // Initialize the images list
  }

  public String getRecommendation() {
    return recommendation;
  }

  public void addImage(String imageName) {
    images.add(imageName); // Add the image to the list
  }

  public ArrayList<String> getImages() {
    return images; // Return the list of images
  }

  @Override
  public void printReviewDetails() { // Print details specific to ExpertReview
    System.out.println("Expert Review by " + getReviewerName() + ":");
    System.out.println("Rating: " + getRating());
    System.out.println("Comments: " + getReviewText());
    System.out.println("Recommendation: " + recommendation);
    if (!images.isEmpty()) { // If there are images, print them
      System.out.println("Images: " + images);
    }
  }
}
