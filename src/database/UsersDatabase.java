package database;

import entertainment.GenresList;
import entertainment.Video;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class UsersDatabase {
    /**
     * the single instance of this class
     */
    private static UsersDatabase instance = null;
    private Map<String, User> users;

    private UsersDatabase() {
        users = new HashMap<>();
    }

    /**
     * @return instance
     */
    public static UsersDatabase getInstance() {
        if (instance == null) {
            instance = new UsersDatabase();
        }

        return instance;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    /**
     * adds a user to the database
     * @param username
     * @param user
     */
    public void addUser(final String username, final User user) {
        users.put(username, user);
    }

    /**
     * populates the database with the users given as input and updates the videoDatabase with
     * the favorite videos of the user
     * @param users
     * @param videosDatabase
     */
    public void populateDatabase(final List<UserInputData> users, final VideosDatabase
            videosDatabase) {
        for (UserInputData userInputData : users) {
            User newUser = new User(userInputData.getUsername(),
                    userInputData.getSubscriptionType(), userInputData.getHistory(),
                    userInputData.getFavoriteMovies(),  videosDatabase);
            this.addUser(newUser.getUsername(),  newUser);
        }
    }

    /**
     * adds the given video to user's favorite list and returns a message with the action result
     * @param username
     * @param videoTitle
     * @return output message
     */
    public String addFavorite(final String username, final String videoTitle) {
        return users.get(username).addFavorite(videoTitle);
    }

    /**
     * adds the given video to user's viewed list and returns a message with the action result
     * @param username
     * @param videoTitle
     * @return output message
     */
    public int addView(final String username, final String videoTitle) {
        return users.get(username).addView(videoTitle);
    }

    /**
     * adds user's rating to video and returns a message with the action result
     * @param username
     * @param videoTitle
     * @param value
     * @param season
     * @return output message
     */
    public String addRating(final String username, final String videoTitle, final double value,
                         final int season) {
        return users.get(username).addRating(videoTitle, value, season);
    }

    /**
     * sorts the users list based on number of ratings given
     * @param numUsers
     * @param sortType
     * @return list with users
     */
    public List<User> getUsersSortedByRatings(final int numUsers, final String
            sortType) {
        Comparator<User> userRatingComp = Comparator
                .comparing(User::getNumRatings)
                .thenComparing(User::getUsername);

        if (sortType.equals("desc")) {
            userRatingComp = userRatingComp.reversed();
        }

        List<User> usersList = new ArrayList<>();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            usersList.add(user);
        }
        return usersList.stream()
                .filter(user -> user.getNumRatings() > 0)
                .sorted(userRatingComp)
                .limit(numUsers)
                .collect(Collectors.toList());
    }

    /**
     * returns the first unseen video for a given user
     * @param username
     * @param videosDatabase
     * @return video title
     */
    public String standardRec(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null) {
            return "error";
        }

        for (String videoTitle : videosDatabase.getVideoTitles()) {
            if (!(user.getHistory().containsKey(videoTitle))) {
                return videoTitle;
            }
        }

        return "error";
    }

    /**
     * returns the first unseen video with the highest rating for a given user
     * @param username
     * @param videosDatabase
     * @return video title
     */
    public String bestUnseen(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null) {
            return "error";
        }

        String videoWithMaxRating = null;
        double maxRating = 0;
        for (String videoTitle : videosDatabase.getVideoTitles()) {
            if (!(user.getHistory().containsKey(videoTitle))) {
                double rating = videosDatabase.getVideos().get(videoTitle)
                        .getRating();
                if (videoWithMaxRating == null) {
                    videoWithMaxRating = videoTitle;
                    maxRating = rating;
                }
                if (Double.compare(maxRating, rating) < 0) {
                    videoWithMaxRating = videoTitle;
                    maxRating = rating;
                }
            }
        }

        if (videoWithMaxRating  == null) {
            return "error";
        } else {
            return videoWithMaxRating;
        }
    }

    /**
     * returns the first unseen video with the most popular genre
     * @param username
     * @param videosDatabase
     * @return video title
     */
    public String popular(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null) {
            return "error";
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            return "error";
        }

        GenresList genreDatabase = new GenresList();
        List<String> popularGenres = genreDatabase
                .sortByPopularity(videosDatabase);

        for (String genreName : popularGenres) {
            for (String videoTitle : videosDatabase.getVideoTitles()) {
                Video video = videosDatabase.getVideos().get(videoTitle);
                if (video.getGenres().contains(genreName)
                        && !user.getHistory().containsKey(videoTitle)) {
                    return videoTitle;
                }
            }
        }

        return "error";
    }

    /**
     * returns the first unseen video with the highest number of occurrences in users' favorite
     * list
     * @param username
     * @param videosDatabase
     * @return video title
     */
    public String favorite(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null) {
            return "error";
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            return "error";
        }

        // sort videos list based on number of occurrences in users' favorite list
        Comparator<Video> videoFavComp = Comparator
                .comparing(Video::getNumFavorite)
                .reversed();
        List<Video> videoList = videosDatabase.getVideos().values().stream()
                .filter(video -> video.getNumFavorite() > 0)
                .filter(video -> !user.getHistory().containsKey(video
                        .getTitle()))
                .sorted(videoFavComp)
                .collect(Collectors.toList());

        if (videoList.isEmpty()) {
            return "error";
        }

        // extract the name of the first video in sorted videos list
        return videoList.get(0).getTitle();
    }

    /**
     * returns a list with the unseen videos of a given genre for a user
     * @param username
     * @param genreName
     * @param videosDatabase
     * @return list of videos
     */
    public List<Video> search(final String username, final String genreName,
                         final VideosDatabase videosDatabase) {
        User user = users.get(username);
        if (user == null) {
            return null;
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            return null;
        }

        Comparator<Video> videoSearchComp = Comparator
                .comparing(Video::getRating)
                .thenComparing(Video::getTitle);

        return videosDatabase.getVideos().values().stream()
                .filter(video -> video.getGenres().contains(genreName))
                .filter(video -> !user.getHistory().containsKey(video.getTitle()))
                .sorted(videoSearchComp)
                .collect(Collectors.toList());
    }

    /**
     * empties the current map of users
     */
    public static void resetDatabase() {
        UsersDatabase.getInstance().users = new HashMap<>();
    }
}
