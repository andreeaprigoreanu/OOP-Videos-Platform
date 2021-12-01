package action;

import database.UsersDatabase;
import database.VideosDatabase;

public final class Command {
    /**
     * for coding style
     */
    private Command() {
    }
    /**
     * adds a video to favorite videos of a user
     * @param username
     * @param videoTitle
     * @param usersDatabase
     * @param videosDatabase
     * @return command result
     */
    public static String favorite(final String username, final String videoTitle,
                                  final UsersDatabase usersDatabase,
                                  final VideosDatabase videosDatabase) {
        String message = usersDatabase.addFavorite(username, videoTitle);

        if (message.equals("is already in favourite list")) {
            return "error -> " + videoTitle + " " + message;
        } else {
            if (message.equals("was added as favourite")) {
                // add favorite video to videoDatabase
                videosDatabase.addFavorite(videoTitle);
                return "success -> " + videoTitle + " " + message;
            } else {
                return "error -> " + videoTitle + " " + message;
            }
        }
    }

    /**
     * adds a video to seen videos of a user
     * @param username
     * @param videoTitle
     * @param usersDatabase
     * @param videosDatabase
     * @return command result
     */
    public static String view(final String username, final String videoTitle,
                              final UsersDatabase usersDatabase,
                              final VideosDatabase videosDatabase) {
        int res = usersDatabase.addView(username, videoTitle);

        videosDatabase.addView(videoTitle);

        return "success -> " + videoTitle + " was viewed with total views of " + res;
    }

    /**
     * adds user's rating to a video
     * @param username
     * @param videoTitle
     * @param value
     * @param season
     * @param usersDatabase
     * @param videosDatabase
     * @return command result
     */
    public static String rateVideo(final String username, final String videoTitle,
                                   final double value, final int season,
                                   final UsersDatabase usersDatabase,
                                   final VideosDatabase videosDatabase) {
        String message = usersDatabase.addRating(username, videoTitle, value, season);

        if (message.equals("has been already rated")) {
            return "error -> " + videoTitle + " " + message;
        } else {
            if (message.equals("was rated")) {
                // add rating to video in videosDatabase
                videosDatabase.addRating(videoTitle, value, season);

                return "success -> " + videoTitle + " was rated with " + value
                        + " by " + username;
            } else {
                return "error -> " + videoTitle + " " + message;
            }
        }
    }
}
