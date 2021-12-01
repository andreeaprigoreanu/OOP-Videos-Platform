package action;

import database.UsersDatabase;
import database.VideosDatabase;
import entertainment.Video;

import java.util.List;

public final class Recommendation {
    public static class Standard {
        /**
         * finds the first unseen video for a given user
         * @param username
         * @param usersDatabase
         * @param videosDatabase
         * @return recommendation result
         */
        public static String getStandardRec(final String username,
                                            final UsersDatabase usersDatabase,
                                            final VideosDatabase videosDatabase) {
            String result = usersDatabase.standardRec(username, videosDatabase);

            if (result.equals("error")) {
                return "StandardRecommendation cannot be applied!";
            } else {
                return "StandardRecommendation result: " + result;
            }
        }

        /**
         * finds the first unseen video with the highest rating for a given user
         * @param username
         * @param usersDatabase
         * @param videosDatabase
         * @return recommendation result
         */
        public static String getBestUnseen(final String username,
                                           final UsersDatabase usersDatabase,
                                           final VideosDatabase videosDatabase) {
            String result = usersDatabase.bestUnseen(username, videosDatabase);

            if (result.equals("error")) {
                return "BestRatedUnseenRecommendation cannot be applied!";
            } else {
                return "BestRatedUnseenRecommendation result: " + result;
            }
        }
    }

    public static class Premium {
        /**
         * finds the first unseen video that has the most popular genre for a given user
         * @param username
         * @param usersDatabase
         * @param videosDatabase
         * @return recommendation result
         */
        public static String getPopular(final String username,
                                        final UsersDatabase usersDatabase,
                                        final VideosDatabase videosDatabase) {
            String result = usersDatabase.popular(username, videosDatabase);

            if (result.equals("error")) {
                return "PopularRecommendation cannot be applied!";
            } else {
                return "PopularRecommendation result: " + result;
            }
        }

        /**
         * finds the first unseen video with the highest occurrence in users' favorite list for a
         * given user
         * @param username
         * @param usersDatabase
         * @param videosDatabase
         * @return recommendation result
         */
        public static String getFavorite(final String username,
                                         final UsersDatabase usersDatabase,
                                         final VideosDatabase videosDatabase) {
            String result = usersDatabase.favorite(username, videosDatabase);

            if (result.equals("error")) {
                return "FavoriteRecommendation cannot be applied!";
            } else {
                return "FavoriteRecommendation result: " + result;
            }
        }

        /**
         * returns a list with the unseen videos that have a common given genre for a given user
         * @param username
         * @param genreName
         * @param usersDatabase
         * @param videosDatabase
         * @return recommendation result
         */
        public static String getSearch(final String username,
                                       final String genreName,
                                       final UsersDatabase usersDatabase,
                                       final VideosDatabase videosDatabase) {
            List<Video> result = usersDatabase.search(username, genreName,
                    videosDatabase);

            if (result == null || result.isEmpty()) {
                return "SearchRecommendation cannot be applied!";
            } else {
                return "SearchRecommendation result: " + result;
            }
        }
    }
}
