package user;

import database.VideosDatabase;
import entertainment.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
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
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;
    /**
     * Ratings given
     */
    private final Map<String, Double> ratings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies,
                final VideosDatabase videosDatabase) {
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

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(final String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public int getNumRatings() {
        return ratings.size();
    }

    /**
     * adds a view to given videos
     * @param videos
     */
    private void addViewsToVideos(final Map<String, Video> videos) {
        for (Map.Entry<String, Integer> entry : history.entrySet()) {
            videos.get(entry.getKey()).addViews(entry.getValue());
        }
    }

    /**
     * increments number of occurrences in user's favorite list of given videos
     * @param videos
     */
    private void addFavoritesToVideos(final Map<String, Video> videos) {
        for (String title : favoriteMovies) {
            videos.get(title).addFavorite();
        }
    }

    /**
     * adds the given video to favorite list
     * @param videoTitle
     * @return message with action result
     */
    public String addFavorite(final String videoTitle) {
        if (favoriteMovies.contains(videoTitle)) {
            return "is already in favourite list";
        } else {
            if (history.containsKey(videoTitle)) {
                favoriteMovies.add(videoTitle);
                return "was added as favourite";
            } else {
                return "is not seen";
            }
        }
    }

    /**
     * adds view to given video
     * @param videoTitle
     * @return new number of views of the given video
     */
    public int addView(final String videoTitle) {
        if (!history.containsKey(videoTitle)) {
            history.put(videoTitle, 1);
        } else {
            history.replace(videoTitle, history.get(videoTitle) + 1);
        }

        return history.get(videoTitle);
    }

    /**
     * adds a rating to given video
     * @param videoTitle
     * @param value
     * @param season
     * @return message with action result
     */
    public String addRating(final String videoTitle, final double value, final int season) {
        String title;
        if (season != 0) {
            title = videoTitle + season;
        } else {
            title = videoTitle;
        }

        if (ratings.containsKey(title)) {
            return "has been already rated";
        }

        if (history.containsKey(videoTitle)) {
            ratings.put(title, value);
            return "was rated";
        }

        return "is not seen";
    }

    @Override
    public String toString() {
        return username;
    }
}
