package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {

  class Operator { // defining the arraylist
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

    public String getName() {
      return this.name;
    }

    public String getCode() {
      return this.code;
    }

    public String getLocation() {
      return this.location;
    }

    public String getStar() {
      return this.star;
    }
  }

  ArrayList<Operator> operators = new ArrayList<>(); // making the main arraylist

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public void searchOperators(String keyword) {
    keyword = keyword.trim().toLowerCase(); // trimming the keyword and making it lowercase

    int numOperatorsFound = 0;
    ArrayList<Operator> foundOperators =
        new ArrayList<>(); // making a new arraylist of operators that are found that match the
    // keyword

    for (int i = 0; i < operators.size(); i++) {
      Operator current = operators.get(i);
      if (operators
              .get(i)
              .getName()
              .toLowerCase()
              .contains(
                  keyword) // searching all of the ararylist for operators that match the keyword
          || operators.get(i).getCode().toLowerCase().contains(keyword)
          || operators.get(i).getLocation().toLowerCase().contains(keyword)
          || operators.get(i).getStar().toLowerCase().contains(keyword)) {
        numOperatorsFound += 1;
        foundOperators.add(current);
      }
    }
    if (numOperatorsFound > 1) {
      MessageCli.OPERATORS_FOUND.printMessage(
          "are",
          String.valueOf(numOperatorsFound),
          "s",
          ":"); // printing how many operators were found
    } else if (numOperatorsFound == 1) {
      MessageCli.OPERATORS_FOUND.printMessage("is", String.valueOf(numOperatorsFound), "", ":");
    } else {
      MessageCli.OPERATORS_FOUND.printMessage("are", "no", "s", ".");
    }

    for (Operator op : foundOperators) {
      MessageCli.OPERATOR_ENTRY.printMessage(
          op.getName(),
          op.getCode(),
          op.getLocation()); // printing out all the operators that were found
    }
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

    String searchName = operatorName.toLowerCase();
    String searchLocation =
        locationAsString
            .toLowerCase(); // initialising what to search for when searching for duplicate
                            // operators

    for (Operator existing :
        operators) { // if there is already an operator with the same name in the same location the
                     // program will exit and say operator already exists
      if (existing.getName().toLowerCase().equals(searchName)
          && existing.getLocation().toLowerCase().equals(searchLocation)) {
        MessageCli.OPERATOR_NOT_CREATED_ALREADY_EXISTS_SAME_LOCATION.printMessage(
            operatorName, locationAsString);
        return;
      }
    }

    int locationCount = 1;
    for (Operator existing : operators) {
      if (existing
          .getLocation()
          .equalsIgnoreCase(locationAsString)) { // checking to see how many people in each location
        locationCount += 1;
      }
    }

    String sequenceNumber =
        String.format(
            "%03d",
            locationCount); // adding on the code at the end depending on how many operators are
                            // currently in that location
    String operatorCode =
        initials.toString() + "-" + abvLocation + "-" + sequenceNumber; // adding it onto the code

    MessageCli.OPERATOR_CREATED.printMessage(
        operatorName, operatorCode, locationAsString); // prints the output
    operators.add(
        new Operator("* ", operatorName, operatorCode, locationAsString)); // adds operator to array
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
