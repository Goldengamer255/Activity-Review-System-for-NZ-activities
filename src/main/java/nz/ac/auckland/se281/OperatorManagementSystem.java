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

  public class Activity {
    private String activityId;
    private String activityName;
    private Types.ActivityType activityType;
    private String operatorName;
    private ArrayList<Review> reviews;

    public Activity(
        String activityId,
        String activityName,
        Types.ActivityType activityType,
        String operatorName) {
      this.activityId = activityId;
      this.activityName = activityName;
      this.activityType = activityType;
      this.operatorName = operatorName;
      this.reviews = new ArrayList<>();
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

    public ArrayList<Review> getReviews() {
      return reviews;
    }

    public void addReview(Review review) {
      this.reviews.add(review);
    }
  }

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
        // print the activity entry
        MessageCli.ACTIVITY_ENTRY.printMessage(
            activity.getActivityName(),
            activity.getActivityId(),
            activity.getActivityType().getName(),
            activity.getOperatorName());
      }
    }
  }

  public void addPublicReview(String activityId, String[] options) {
    activityId = activityId.toUpperCase(); // Convert activityId to uppercase for consistency
    if (activityId == null || activityId.isEmpty()) { // Check if activityId is null or empty
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // Check if the activity ID matches
          // Generate the review ID based on the size of the reviews list
          String reviewId = activityId + "-R" + (activity.getReviews().size() + 1);

          // Create a new PublicReview object
          String reviewerName = options[0];
          int rating = Integer.parseInt(options[2]);
          String comments = options[3];
          boolean isAnonymous = options[1].equalsIgnoreCase("y");

          PublicReview publicReview =
              new PublicReview(comments, rating, reviewerName, reviewId, isAnonymous);

          // Add the review to the activity
          activity.addReview(publicReview);

          // Print confirmation message
          MessageCli.REVIEW_ADDED.printMessage("Public", reviewId, activity.getActivityName());
          return; // Exit after successfully adding the review
        }
      }
    }
    // If no matching activity is found
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
  }

  public void addPrivateReview(String activityId, String[] options) {
    activityId = activityId.toUpperCase(); // Convert activityId to uppercase for consistency
    if (activityId == null || activityId.isEmpty()) { // Check if activityId is null or empty
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // check if the activity id matches
          String reviewId = activityId + "-R" + (activity.getReviews().size() + 1);

          // Extract review details from options
          String reviewerName = options[0];
          String email = options[1];
          int rating = Integer.parseInt(options[2]);
          String comments = options[3];
          boolean followUpRequested = options[4].equalsIgnoreCase("y");

          // Create and add the review
          PrivateReview privateReview =
              new PrivateReview(reviewerName, email, rating, comments, followUpRequested);
          privateReview.setReviewId(reviewId); // Set the review ID
          activity.addReview(privateReview);

          MessageCli.REVIEW_ADDED.printMessage("Private", reviewId, activity.getActivityName());
          return;
        }
      }
    }
    // If no matching activity is found
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
  }

  public void addExpertReview(String activityId, String[] options) {
    activityId = activityId.toUpperCase(); // Convert activityId to uppercase for consistency
    if (activityId == null || activityId.isEmpty()) {
      MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
      return;
    }

    for (int i = 0; i < operators.size(); i++) { // Loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // Loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // Check if the activity ID matches
          // Generate the review ID based on the size of the reviews list
          String reviewId = activityId + "-R" + (activity.getReviews().size() + 1);

          // Create a new ExpertReview object
          String reviewerName = options[0];
          int rating = Integer.parseInt(options[1]);
          String comments = options[2];
          String recommendation = options[3];

          ExpertReview expertReview =
              new ExpertReview(comments, rating, reviewerName, reviewId, recommendation);

          // Add the review to the activity
          activity.addReview(expertReview);

          // If no matching activity is found
          MessageCli.REVIEW_ADDED.printMessage("Expert", reviewId, activity.getActivityName());
          return;
        }
      }
    }
    // If no matching activity is found
    MessageCli.REVIEW_NOT_ADDED_INVALID_ACTIVITY_ID.printMessage(activityId);
  }

  public void displayReviews(String activityId) {
    activityId = activityId.toUpperCase(); // Convert activityId to uppercase for consistency
    if (activityId == null || activityId.isEmpty()) { // Check if activityId is null or empty
      MessageCli.ACTIVITY_NOT_FOUND.printMessage(activityId);
      return;
    }
    for (int i = 0; i < operators.size(); i++) { // loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // loop through the activities
        Activity activity = operator.getActivities().get(j);
        if (activity.getActivityId().equals(activityId)) { // check if the activity id matches
          ArrayList<Review> reviews = activity.getReviews(); // Get the list of reviews

          if (reviews.isEmpty()) { // Check if there are no reviews
            MessageCli.REVIEWS_FOUND.printMessage("are", "no", "s", activity.getActivityName());
            return;
          }

          // Print the header with the number of reviews
          String verb = reviews.size() == 1 ? "is" : "are";
          String singularOrPlural = reviews.size() == 1 ? "" : "s";
          MessageCli.REVIEWS_FOUND.printMessage(
              verb, String.valueOf(reviews.size()), singularOrPlural, activity.getActivityName());

          // Loop through the reviews and display each one
          for (int k = 0; k < reviews.size(); k++) {
            Review review = reviews.get(k);
            String reviewType =
                review instanceof PublicReview
                    ? "Public"
                    : review instanceof PrivateReview ? "Private" : "Expert";

            String reviewerName =
                review instanceof PublicReview && ((PublicReview) review).isAnonymous()
                    ? "Anonymous"
                    : review.getReviewerName();

            MessageCli.REVIEW_ENTRY_HEADER.printMessage(
                String.valueOf(review.getRating()),
                "5",
                reviewType,
                review.getReviewId(),
                reviewerName);

            MessageCli.REVIEW_ENTRY_REVIEW_TEXT.printMessage(review.getReviewText());

            if (review instanceof PublicReview && ((PublicReview) review).isEndorsed()) {
              MessageCli.REVIEW_ENTRY_ENDORSED.printMessage();
            } else if (review instanceof PrivateReview) {
              PrivateReview privateReview = (PrivateReview) review;
              if (privateReview.isFollowUpRequested()) {
                if (privateReview.getOperatorResponse() != null) {
                  MessageCli.REVIEW_ENTRY_RESOLVED.printMessage(
                      privateReview.getOperatorResponse());
                } else {
                  MessageCli.REVIEW_ENTRY_FOLLOW_UP.printMessage(privateReview.getEmail());
                }
              } else {
                MessageCli.REVIEW_ENTRY_RESOLVED.printMessage("-");
              }
            } else if (review instanceof ExpertReview) {
              ExpertReview expertReview = (ExpertReview) review;
              if (expertReview.getRecommendation().equalsIgnoreCase("y")) {
                MessageCli.REVIEW_ENTRY_RECOMMENDED.printMessage();
              }
              if (!expertReview.getImages().isEmpty()) {
                String images = String.join(",", expertReview.getImages());
                MessageCli.REVIEW_ENTRY_IMAGES.printMessage(images);
              }
            }
          }
          return; // Exit after displaying all reviews
        }
      }
      MessageCli.ACTIVITY_NOT_FOUND.printMessage(activityId);
    }
  }

  public void endorseReview(String reviewId) {
    for (int i = 0; i < operators.size(); i++) { // Loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // Loop through the activities
        Activity activity = operator.getActivities().get(j);
        for (int k = 0; k < activity.getReviews().size(); k++) { // Loop through the reviews
          Review review = activity.getReviews().get(k);
          if (review.getReviewId().equals(reviewId)) { // Check if the review ID matches
            if (review instanceof PublicReview) { // Check if it's a public review
              PublicReview publicReview = (PublicReview) review;
              publicReview.endorse(); // Endorse the review
              MessageCli.REVIEW_ENDORSED.printMessage(reviewId); // Print success message
              return;
            } else {
              // If the review is not a public review
              MessageCli.REVIEW_NOT_ENDORSED.printMessage(reviewId);
              return;
            }
          }
        }
      }
    }

    // If no matching review is found
    MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
  }

  public void resolveReview(String reviewId, String response) {
    for (int i = 0; i < operators.size(); i++) { // Loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // Loop through the activities
        Activity activity = operator.getActivities().get(j);
        for (int k = 0; k < activity.getReviews().size(); k++) { // Loop through the reviews
          Review review = activity.getReviews().get(k);
          if (review.getReviewId().equals(reviewId)) { // Check if the review ID matches
            if (review instanceof PrivateReview) { // Check if it's a private review
              PrivateReview privateReview = (PrivateReview) review;

              if (privateReview.isFollowUpRequested()) { // Ensure follow-up was requested
                privateReview.respond(response); // Resolve the review with the response
                MessageCli.REVIEW_RESOLVED.printMessage(reviewId); // Print success message
                return;
              } else {
                // If follow-up was not requested, resolving is not allowed
                MessageCli.REVIEW_NOT_RESOLVED.printMessage(reviewId);
                return;
              }
            } else {
              // If the review is not a private review
              MessageCli.REVIEW_NOT_RESOLVED.printMessage(reviewId);
              return;
            }
          }
        }
      }
    }

    // If no matching review is found
    MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
  }

  public void uploadReviewImage(String reviewId, String imageName) {
    for (int i = 0; i < operators.size(); i++) { // Loop through the operators
      Operator operator = operators.get(i);
      for (int j = 0; j < operator.getActivities().size(); j++) { // Loop through the activities
        Activity activity = operator.getActivities().get(j);
        for (int k = 0; k < activity.getReviews().size(); k++) { // Loop through the reviews
          Review review = activity.getReviews().get(k);
          if (review.getReviewId().equals(reviewId)) { // Check if the review ID matches
            if (review instanceof ExpertReview) { // Check if it's an expert review
              ExpertReview expertReview = (ExpertReview) review;

              // Add the image to the expert review
              expertReview.addImage(imageName);

              // Print success message
              MessageCli.REVIEW_IMAGE_ADDED.printMessage(imageName, reviewId);
              return;
            } else {
              // If the review is not an expert review
              MessageCli.REVIEW_IMAGE_NOT_ADDED_NOT_EXPERT.printMessage(reviewId);
              return;
            }
          }
        }
      }
    }

    // If no matching review is found
    MessageCli.REVIEW_NOT_FOUND.printMessage(reviewId);
  }

  public void displayTopActivities() {
    Activity topActivity = null;
    double highestAverageRating = 0.0;

    for (Types.Location location : Types.Location.values()) { // Loop through all locations
      String locationName = location.getFullName();

      for (Operator operator : operators) { // Loop through operators
        if (operator
            .getLocation()
            .equalsIgnoreCase(locationName)) { // Check if operator is in the location
          for (Activity activity : operator.getActivities()) { // Loop through activities
            double totalRating = 0.0;
            int reviewCount = 0;

            for (Review review : activity.getReviews()) { // Loop through reviews
              if (review instanceof PublicReview || review instanceof ExpertReview) {
                totalRating += review.getRating();
                reviewCount++;
              }
            }

            if (reviewCount > 0) { // Calculate average rating
              double averageRating = totalRating / reviewCount;
              if (averageRating > highestAverageRating) { // Check if it's the highest rating
                highestAverageRating = averageRating;
                topActivity = activity;
              }
            }
          }
        }
      }

      if (topActivity != null) {
        // Print the top activity for the location
        MessageCli.TOP_ACTIVITY.printMessage(
            locationName,
            topActivity.getActivityName(),
            String.format("%.1f", highestAverageRating));
      } else {
        // Print no reviewed activities found for the location
        MessageCli.NO_REVIEWED_ACTIVITIES.printMessage(locationName);
      }
    }
  }
}
