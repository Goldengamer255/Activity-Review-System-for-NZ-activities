package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {
    keyword = keyword.trim().toLowerCase();
    MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
  }

  public void createOperator(String operatorName, String location) {
    Location locationFound =
        Location.fromString(location); // gets the location from what the user inputted
    String locationAsString =
        locationFound.getFullName(); // converts whatever is obtained to full name for printing

    String[] words = operatorName.split(" ");
    StringBuilder initials =
        new StringBuilder(); // is used to concatenate the first letters of each word

    for (String word : words) {
      if (!word.isEmpty()) { // Checking the input from user to see if the word is not empty
        initials.append(word.charAt(0)); // This gets the first letter from each word
      }
    }
    String abvLocation = locationFound.getLocationAbbreviation();
    initials.append("-" + abvLocation + "-001");
    MessageCli.OPERATOR_CREATED.printMessage(
        operatorName, initials.toString(), locationAsString); // prints the output

    class Operator {
      private String star;
      private String name;
      private String code;
      private String location;

      public Operator(String star, String name, String code, String location) {
        this.star = star;
        this.name = name;
        this.code = code;
        this.location = location;
      }

      public String getStar() {
        return star;
      }

      public String getName() {
        return name;
      }

      public String getCode() {
        return code;
      }

      public String getLocation() {
        return location;
      }
    }

    ArrayList<Operator> operators = new ArrayList<>();
    operators.add(new Operator("* ", operatorName, initials.toString(), locationAsString));
  }

  public void viewActivities(String operatorId) {
    // TODO implement
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    // TODO implement
  }

  public void searchActivities(String keyword) {
    // TODO implement
  }

  public void addPublicReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addPrivateReview(String activityId, String[] options) {
    // TODO implement
  }

  public void addExpertReview(String activityId, String[] options) {
    // TODO implement
  }

  public void displayReviews(String activityId) {
    // TODO implement
  }

  public void endorseReview(String reviewId) {
    // TODO implement
  }

  public void resolveReview(String reviewId, String response) {
    // TODO implement
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    // TODO implement
  }

  public void displayTopActivities() {
    // TODO implement
  }
}
