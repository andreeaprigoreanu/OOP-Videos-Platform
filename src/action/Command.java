package action;

import database.UsersDatabase;
import database.VideosDatabase;
import entertainment.Video;

public final class Command {
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

    public static String view(final String username, final String videoTitle,
                              final UsersDatabase usersDatabase,
                              final VideosDatabase videosDatabase) {
        int res = usersDatabase.addView(username, videoTitle);

        videosDatabase.addView(videoTitle);

        return "success -> " + videoTitle + " was viewed with total views of " + res;
    }

    public static String rateVideo(final String username, final String videoTitle,
                                   double value, int season,
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
