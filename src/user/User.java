package user;

import database.VideosDatabase;
import entertainment.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    /**
     * User's username
     */
    private String username;
    /**
     * Subscription Type
     */
    private String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private ArrayList<String> favoriteMovies;

    private final Map<String, Double> ratings;

    public User(String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies,
                VideosDatabase videosDatabase) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;

        Map<String, Video> videos = videosDatabase.getVideos();
        this.addViewsToVideos(videos);
        this.addFavoritesToVideos(videos);

        this.ratings = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public int getNumRatings() {
        return ratings.size();
    }

    private void addViewsToVideos(Map<String, Video> videos) {
        for (Map.Entry<String, Integer> entry : history.entrySet()) {
            videos.get(entry.getKey()).addViews(entry.getValue());
        }
    }

    private void addFavoritesToVideos(Map<String, Video> videos) {
        for (String title : favoriteMovies) {
            videos.get(title).addFavorite();
        }
    }

    public int addFavorite(String videoTitle) {
        if (favoriteMovies.contains(videoTitle)) {
            return 0;
        } else {
            if (history.containsKey(videoTitle)) {
                favoriteMovies.add(videoTitle);
                return 1;
            } else {
                return 2;
            }
        }
    }

    public int addView(String videoTitle) {
        if (!history.containsKey(videoTitle)) {
            history.put(videoTitle, 1);
        } else {
            history.replace(videoTitle, history.get(videoTitle) + 1);
        }

        return history.get(videoTitle);
    }

    public int addRating(String videoTitle, double value, int season) {
        String title  = videoTitle + " " + season;

        if (ratings.containsKey(title)) {
            return 0;
        }

        if (history.containsKey(videoTitle)) {
            ratings.put(title, value);
            return 1;
        }

        return 2;
    }

    @Override
    public String toString() {
        return username;
    }
}
