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
        int res = usersDatabase.addFavorite(username, videoTitle);

        if (res == 0) {
            return "error -> " + videoTitle + " is already in favourite list";
        } else {
            if (res == 1) {
                // add video to videDatabase
                videosDatabase.addFavorite(videoTitle);
                return "success -> " + videoTitle + " was added as favourite";
            } else {
                return "error -> " + videoTitle + " is not seen";
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
        int res = usersDatabase.addRating(username, videoTitle, value, season);

        if (res == 0) {
            return "error -> " + videoTitle + " has been already rated";
        } else {
            if (res == 1) {
                videosDatabase.addRating(videoTitle, value, season);

                return "success -> " + videoTitle + " was rated with " + value
                        + " by " + username;
            } else {
                return "error -> " + videoTitle + " is not seen";
            }
        }
    }
}
