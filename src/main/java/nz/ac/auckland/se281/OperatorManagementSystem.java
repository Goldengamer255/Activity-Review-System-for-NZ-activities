package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {

  class Operator {
    private String star;
    private String name;
    private String code;
    private String location;
    private ArrayList<String> activities; // New field for activities

    public Operator(String star, String name, String code, String location) {
      this.star = star;
      this.name = name;
      this.code = code;
      this.location = location;
      this.activities = new ArrayList<>(); // Initialize activities list
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

    public ArrayList<String> getActivities() {
      return this.activities;
    }

    public void addActivity(String activity) {
      this.activities.add(activity);
    }
  }

  ArrayList<Operator> operators = new ArrayList<>(); // making the main arraylist

  // make an arraylist for operators that contain their code corresponding to any activites that
  // they have which can be added to the arraylist
  ArrayList<String> activities = new ArrayList<>(); // making the arraylist for activities

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
    String nameCheck = "";
    nameCheck = operatorName.replaceAll("\\s", "");
    if (nameCheck.length() < 3) {
      MessageCli.OPERATOR_NOT_CREATED_INVALID_OPERATOR_NAME.printMessage(operatorName);
      return;
    }

    Location locationFound;
    String locationAsString;
    try {
      locationFound =
          Location.fromString(location); // May throw IllegalArgumentException or return null
      if (locationFound == null) { // Extra check if method returns null
        throw new IllegalArgumentException("Invalid location: " + location);
      }
      locationAsString = locationFound.getFullName();
    } catch (IllegalArgumentException e) {
      // Handle invalid location
      MessageCli.OPERATOR_NOT_CREATED_INVALID_LOCATION.printMessage(location);
      return; // Exit early
    }

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
        operatorName, operatorCode.toUpperCase(), locationAsString); // prints the output
    operators.add(
        new Operator(
            "* ",
            operatorName,
            operatorCode.toUpperCase(),
            locationAsString)); // adds operator to array
  }

  public void viewActivities(String operatorId) {
    if (operatorId == null || operatorId.isEmpty()) {
      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
      return;
    }

    String searchId = operatorId.toLowerCase();
    for (Operator current : operators) {
      if (current.getCode().toLowerCase().equals(searchId)) {
        ArrayList<String> activities = current.getActivities();
        if (activities.isEmpty()) {
          MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
        } else {
          MessageCli.ACTIVITIES_FOUND.printMessage(
              "are", String.valueOf(activities.size()), "ies", ":");
          for (String activity : activities) {
            MessageCli.ACTIVITY_ENTRY.printMessage(activity);
          }
        }
        return;
      }
    }

    MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId); // If operator not found
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    if (operatorId == null || operatorId.isEmpty()) { // Check if operatorId is null or empty
      MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
      return;
    }

    if (activityName.trim().length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(
          activityName); // Check activity name length
      return;
    }

    String searchId = operatorId.toLowerCase();
    for (Operator current : operators) {
      if (current.getCode().toLowerCase().equals(searchId)) {
        String activity = activityName + " (" + activityType + ")";
        current.addActivity(activity); // Add activity to the operator
        MessageCli.ACTIVITY_CREATED.printMessage(activityName, activityType, operatorId);
        return;
      }
    }

    MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId); // If operator not found
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
