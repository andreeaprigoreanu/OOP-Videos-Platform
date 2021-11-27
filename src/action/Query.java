package action;

import actor.Actor;
import database.ActorsDatabase;
import database.UsersDatabase;
import database.VideosDatabase;
import entertainment.Video;
import user.User;

import java.util.List;

public class Query {
    public static class ActorsQuery {
        public static String sortByAverage(final int numActors, final String sortType,
                                           final ActorsDatabase actorsDatabase,
                                           final VideosDatabase videosDatabase) {
            List<Actor> sortedActors = actorsDatabase.
                    getActorsSortedByAvgRating(numActors, sortType, videosDatabase);

            return "Query result: " + sortedActors;
        }

        public static String sortByAwards(final List<String> awards,
                                          final String sortType,
                                          final ActorsDatabase actorsDatabase) {
            List<Actor> sortedActors = actorsDatabase.
                    getActorsSortedByAwards(awards, sortType);

            return "Query result: " + sortedActors;
        }

        public static String sortByDescription(final List<String> keywords,
                                               final String sortType,
                                               final ActorsDatabase actorsDatabase) {
            List<Actor> sortedActors = actorsDatabase
                    .getActorsSortedByKeywords(keywords, sortType);

            return "Query result: " + sortedActors;
        }
    }

    public static class VideosQuery {
        public static String sortByRating(final int numVideos, final String sortType,
                                          final String year, final String genre,
                                          final String videoType,
                                          final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortByRating(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }

        public static String sortByNumFavorite(final int numVideos, final String sortType,
                                               final String year, final String genre,
                                               final String videoType,
                                               final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortedByNumFavorite(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }

        public static String sortByDuration(final int numVideos, final String sortType,
                                            final String year, final String genre,
                                            final String videoType,
                                            final VideosDatabase videosDatabase) {
            List<Video> sortedVideos = videosDatabase
                    .getVideosSortedByDuration(numVideos, sortType, year, genre,
                            videoType);

            return "Query result: " + sortedVideos;
        }

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
        public static String sortByNumRatings(final int numUsers, final String sortType,
                                              final UsersDatabase usersDatabase) {
            List<User> sortedUsers = usersDatabase
                    .getUsersSortedByRatings(numUsers, sortType);

            return "Query result: " + sortedUsers;
        }
    }
}
