package action;

import database.UsersDatabase;
import database.VideosDatabase;
import entertainment.Video;

import java.util.List;

public class Recommendation {
	public static class Standard {
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
