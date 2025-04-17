package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Types.Location;

public class OperatorManagementSystem {

  class Operator {
    private String star;
    private String name;
    private String code;
    private String location;
    private ArrayList<Activity> activities; // New field for activities

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

    public ArrayList<Activity> getActivities() {
      return this.activities;
    }

    public void addActivity(Activity activity) {
      this.activities.add(activity);
    }
  }

  private ArrayList<Operator> operators = new ArrayList<>(); // making the main arraylist

  // Do not change the parameters of the constructor
  public OperatorManagementSystem() {}

  public class Activity {
    private String activityId;
    private String activityName;
    private Types.ActivityType activityType;
    private String operatorName;

    public Activity(
        String activityId,
        String activityName,
        Types.ActivityType activityType,
        String operatorName) {
      this.activityId = activityId;
      this.activityName = activityName;
      this.activityType = activityType;
      this.operatorName = operatorName;
    }

    public String getActivityId() {
      return activityId;
    }

    public String getActivityName() {
      return activityName;
    }

    public Types.ActivityType getActivityType() {
      return activityType;
    }

    public String getOperatorName() {
      return operatorName;
    }

    private ArrayList<Review> reviews = new ArrayList<>();

    public ArrayList<Review> getReviews() {
      return reviews;
    }

    public void addReview(Review review) {
      this.reviews.add(review);
      MessageCli.REVIEW_ADDED.printMessage(
          review.getClass().getSimpleName(), this.activityId, this.activityName);
    }
  }

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
      return;
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
        ArrayList<Activity> activities = current.getActivities();
        if (activities.isEmpty()) {
          // No activities found for the operator
          MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
        } else {
          // Print the header with the number of activities
          String verb = activities.size() == 1 ? "is" : "are";
          String singularOrPlural = activities.size() == 1 ? "y" : "ies";
          MessageCli.ACTIVITIES_FOUND.printMessage(
              verb, String.valueOf(activities.size()), singularOrPlural, ":");

          // Print each activity
          for (Activity activity : activities) {
            MessageCli.ACTIVITY_ENTRY.printMessage(
                activity.getActivityName(),
                activity.getActivityId(),
                activity.getActivityType().getName(),
                activity.getOperatorName());
          }
        }
        return;
      }
    }

    // Operator not found
    MessageCli.OPERATOR_NOT_FOUND.printMessage(operatorId);
  }

  public void createActivity(String activityName, String activityType, String operatorId) {
    if (operatorId == null || operatorId.isEmpty()) { // Check if operatorId is null or empty
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(operatorId);
      return;
    }

    // Remove surrounding spaces and all spaces for length validation
    String nameCheck = activityName.trim().replaceAll("\\s", "");
    if (nameCheck.length() < 3) {
      MessageCli.ACTIVITY_NOT_CREATED_INVALID_ACTIVITY_NAME.printMessage(activityName);
      return;
    }

    Types.ActivityType validActivityType =
        Types.ActivityType.fromString(activityType); // Check activity type

    String searchId = operatorId.toLowerCase();
    for (Operator current : operators) {
      if (current.getCode().toLowerCase().equals(searchId)) {
        // Get the operator's name
        String operatorName = current.getName();

        // Determine the activity number for this operator
        int activityCount = current.getActivities().size() + 1;
        String activityNumber = String.format("%03d", activityCount); // Format as three digits

        // Generate the activity ID
        String activityId = operatorId.toUpperCase() + "-" + activityNumber;

        // Create the activity string with the valid activity type
        Activity activity = new Activity(activityId, activityName, validActivityType, operatorName);
        current.addActivity(activity);

        MessageCli.ACTIVITY_CREATED.printMessage(
            activityName, activityId, validActivityType.getName(), operatorName);
        return;
      }
    }

    MessageCli.ACTIVITY_NOT_CREATED_INVALID_OPERATOR_ID.printMessage(
        operatorId); // If operator not found
  }

  public void searchActivities(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) { // Check if keyword is null or empty
      MessageCli.ACTIVITY_NOT_FOUND.printMessage(keyword);
      return;
    }

    keyword = keyword.trim().toLowerCase(); // Normalize the keyword for case-insensitive search
    ArrayList<Activity> matchingActivities = new ArrayList<>();

    Types.Location matchedLocation =
        Types.Location.fromString(keyword); // Check if keyword is a location

    for (Operator operator : operators) {
      for (Activity activity : operator.getActivities()) {
        // Check if the keyword matches activity name, type, or operator location
        if (keyword.equals("*") // If the keyword is "*", add all activities
            || activity.getActivityName().toLowerCase().contains(keyword)
            || activity.getActivityType().getName().toLowerCase().contains(keyword)
            || (matchedLocation != null
                && operator.getLocation().equalsIgnoreCase(matchedLocation.getFullName()))) {
          matchingActivities.add(activity);
        }
      }
    }

    if (matchingActivities.isEmpty()) { // No matching activities found
      MessageCli.ACTIVITIES_FOUND.printMessage("are", "no", "ies", ".");
    } else {
      // Print the header with the number of matching activities
      String verb = matchingActivities.size() == 1 ? "is" : "are"; // Singular or plural verb
      String singularOrPlural = matchingActivities.size() == 1 ? "y" : "ies"; // Singular or plural
      MessageCli.ACTIVITIES_FOUND.printMessage(
          verb, String.valueOf(matchingActivities.size()), singularOrPlural, ":");

      // Print each matching activity
      for (Activity activity : matchingActivities) { // loop through the activities
        MessageCli.ACTIVITY_ENTRY.printMessage( // print the activity entry
            activity.getActivityName(),
            activity.getActivityId(),
            activity.getActivityType().getName(),
            activity.getOperatorName());
      }
    }
  }

  public void addPublicReview(String activityId, String[] options) {
    if (activityId == null || activityId.isEmpty()) { // Check if activityId is null or empty
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        try {
          Types.ActivityType activityType = activity.getActivityType(); // get the activity type
          if (activityType == null) { // check if the activity type is null
            throw new NullPointerException("Activity type is null");
          }
        } catch (NullPointerException e) {
          // Handle the case where activity type is null
          MessageCli.ACTIVITY_NOT_FOUND.printMessage(activityId);
          return;
        }
      }
    }

    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // check if the activity id matches
          String activityName = activity.getActivityName(); // get the activity name
          MessageCli.REVIEW_ADDED.printMessage("Public review", activityId, activityName);
          return; // Exit after adding the review
        }
      }
    }
    // If no matching activity is found
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
  }

  public void addPrivateReview(String activityId, String[] options) {
    if (activityId == null || activityId.isEmpty()) { // Check if activityId is null or empty
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }
    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // check if the activity id matches
          String activityName = activity.getActivityName(); // get the activity name
          MessageCli.REVIEW_ADDED.printMessage("Private", activityId, activityName);
          return; // Exit after adding the review
        }
      }
    }
    // If no matching activity is found
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
  }

  public void addExpertReview(String activityId, String[] options) {
    if (activityId == null || activityId.isEmpty()) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    for (Operator operator : operators) {
      for (Activity activity : operator.getActivities()) {
        if (activity.getActivityId().equals(activityId)) {
          // Extract review details from options
          String reviewerName = options[0];
          int rating = Integer.parseInt(options[1]);
          String reviewText = options[2];
          String reviewId = "EXP-" + activityId + "-" + (activity.getReviews().size() + 1);

          // Create and add the review
          // Review review = new PublicReview(reviewText, rating, reviewerName, reviewId);
          // activity.addReview(review);

          // Print confirmation
          MessageCli.REVIEW_ADDED.printMessage("Expert", activityId, activity.getActivityName());
          return;
        }
      }
    }

    // If no matching activity is found
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
  }

  public void displayReviews(String activityId) {
    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // check if the activity id matches
          String activityName = activity.getActivityName(); // get the activity name

          MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", activityName);
          return;
        }
      }
    }
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
