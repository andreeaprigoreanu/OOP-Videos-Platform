package database;

import entertainment.Video;
import user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsersDatabase {
    private static UsersDatabase instance = null;
    private Map<String, User> users;

    private UsersDatabase() {
        users = new HashMap<>();
    }

    public static UsersDatabase getInstance() {
        if (instance == null) {
            instance = new UsersDatabase();
        }

        return instance;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void addUser(String username, User user) {
        users.put(username, user);
    }

    public int addFavorite(String username, String videoTitle) {
        return users.get(username).addFavorite(videoTitle);
    }

    public int addView(String username, String videoTitle) {
        return users.get(username).addView(videoTitle);
    }

    public int addRating(String username, String videoTitle, double value,
                         int season) {
        return users.get(username).addRating(videoTitle, value, season);
    }

    public List<User> getUsersSortedByRatings(final int numUsers, final String
            sortType) {
        Comparator<User> userRatingComp = Comparator
                .comparing(User::getNumRatings)
                .thenComparing(User::getUsername);

        if (sortType.equals("desc")) {
            userRatingComp = userRatingComp.reversed();
        }

        List<User> usersList = new ArrayList<>();
        for(Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            usersList.add(user);
        }
        return usersList.stream()
                .filter(user -> user.getNumRatings() > 0)
                .sorted(userRatingComp)
                .limit(numUsers)
                .collect(Collectors.toList());
    }

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

    public String bestUnseen(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null) {
            return "error";
        }

        String result = null;
        double maxRating = 0;
        for (String videoTitle : videosDatabase.getVideoTitles()) {
            if (!(user.getHistory().containsKey(videoTitle))) {
                double rating = videosDatabase.getVideos().get(videoTitle)
                        .getRating();
                if (result == null) {
                    result = videoTitle;
                    maxRating = rating;
                }
                if (Double.compare(rating, maxRating) > 0) {
                    result = videoTitle;
                    maxRating = rating;
                }
            }
        }

        if (result  == null) {
            return "error";
        } else {
            return result;
        }
    }

    public String popular(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null ) {
            return "error";
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            return "error";
        }

        GenreDatabase genreDatabase = new GenreDatabase();
        List<String> popularGenres = genreDatabase
                .sortByPopularity(videosDatabase);

        for (String genreName : popularGenres) {
            for (String videoTitle : videosDatabase.getVideoTitles()) {
                Video video = videosDatabase.getVideos().get(videoTitle);
                if (video.getGenres().contains(genreName) &&
                        !user.getHistory().containsKey(videoTitle)) {
                    return videoTitle;
                }
            }
        }

        return "error";
    }

    public String favorite(final String username, final VideosDatabase
            videosDatabase) {
        User user = users.get(username);
        if (user == null ) {
            return "error";
        }

        if (user.getSubscriptionType().equals("BASIC")) {
            return "error";
        }

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

        return videoList.get(0).getTitle();
    }

    public List<Video> search(final String username, final String genreName,
                         final VideosDatabase videosDatabase) {
        User user = users.get(username);
        if (user == null ) {
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

    public static void resetDatabase() {
        UsersDatabase.getInstance().users = new HashMap<>();
    }
}
