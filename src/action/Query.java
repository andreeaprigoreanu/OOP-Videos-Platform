package action;

import actor.Actor;
import database.ActorsDatabase;
import database.UsersDatabase;
import database.VideosDatabase;
import entertainment.Video;
import user.User;

import java.util.List;

public final class Query {
    public static class ActorsQuery {
        /**
         * sorts actors in actorsDatabase based on the average rating of the movies they starred in
         * @param numActors
         * @param sortType
         * @param actorsDatabase
         * @param videosDatabase
         * @return query result
         */
        public static String sortByAverage(final int numActors, final String sortType,
                                           final ActorsDatabase actorsDatabase,
                                           final VideosDatabase videosDatabase) {
            List<Actor> sortedActors = actorsDatabase.
                    getActorsSortedByAvgRating(numActors, sortType, videosDatabase);

            return "Query result: " + sortedActors;
        }

        /**
         * sorts actors in actorsDatabase based on number of movies they starred in
         * @param awards
         * @param sortType
         * @param actorsDatabase
         * @return query result
         */
        public static String sortByAwards(final List<String> awards,
                                          final String sortType,
                                          final ActorsDatabase actorsDatabase) {
            List<Actor> sortedActors = actorsDatabase.
                    getActorsSortedByAwards(awards, sortType);

            return "Query result: " + sortedActors;
        }

        /**
         * sorts actors in actorsDatabase based on keywords that appear in their description
         * @param keywords
         * @param sortType
         * @param actorsDatabase
         * @return query result
         */
        public static String sortByDescription(final List<String> keywords,
                                               final String sortType,
                                               final ActorsDatabase actorsDatabase) {
            List<Actor> sortedActors = actorsDatabase
                    .getActorsSortedByKeywords(keywords, sortType);

            return "Query result: " + sortedActors;
        }
    }

    public static class VideosQuery {
        /**
         * sorts videos in videosDatabase based on rating
         * @param numVideos
         * @param sortType
         * @param year
         * @param genre
         * @param videoType
         * @param videosDatabase
         * @return
         */
        public static String sortByRating(final int numVideos, final String sortType,
                                          final String year, final String genre,
                                          final String videoType,
                                          final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortByRating(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }

        /**
         * sorts videos in videosDatabase based on number of occurrences in users' favorite list
         * @param numVideos
         * @param sortType
         * @param year
         * @param genre
         * @param videoType
         * @param videosDatabase
         * @return query result
         */
        public static String sortByNumFavorite(final int numVideos, final String sortType,
                                               final String year, final String genre,
                                               final String videoType,
                                               final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortedByNumFavorite(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }

        /**
         * sorts videos in videosDatabase based on duration
         * @param numVideos
         * @param sortType
         * @param year
         * @param genre
         * @param videoType
         * @param videosDatabase
         * @return query result
         */
        public static String sortByDuration(final int numVideos, final String sortType,
                                            final String year, final String genre,
                                            final String videoType,
                                            final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortedByDuration(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }

        /**
         * sorts videos in videosDatabase based on number of views
         * @param numVideos
         * @param sortType
         * @param year
         * @param genre
         * @param videoType
         * @param videosDatabase
         * @return query result
         */
        public static String sortByMostViewed(final int numVideos, final String sortType,
                                              final String year, final String genre,
                                              final String videoType,
                                              final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortedByMostViewed(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }
    }

    public static class UsersQuery {
        /**
         * sorts users in usersDatabase based on number of ratings given
         * @param numUsers
         * @param sortType
         * @param usersDatabase
         * @return query result
         */
        public static String sortByNumRatings(final int numUsers, final String sortType,
                                              final UsersDatabase usersDatabase) {
            List<User> sortedUsers = usersDatabase
                    .getUsersSortedByRatings(numUsers, sortType);

            return "Query result: " + sortedUsers;
        }
    }
}
