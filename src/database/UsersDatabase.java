package database;

import user.User;

import java.util.*;
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

    public List<User> getUsersSortedByRatings(final int numUsers, final String sortType) {
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

    public static void resetDatabase() {
        UsersDatabase.getInstance().users = new HashMap<>();
    }
}
