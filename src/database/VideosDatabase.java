package database;

import entertainment.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VideosDatabase {
	private static VideosDatabase instance = null;
	private Map<String, Video> videos;
	private List<String> videoTitles;

	private VideosDatabase() {
		videos = new HashMap<>();
		videoTitles = new ArrayList<>();
	}

	public static VideosDatabase getInstance() {
		if (instance == null) {
			instance = new VideosDatabase();
		}

		return instance;
	}

	public Map<String, Video> getVideos() {
		return videos;
	}

	public List<String> getVideoTitles() {
		return videoTitles;
	}

	public void addVideo(String title, Video video) {
		videos.put(title, video);
		videoTitles.add(title);
	}

	public void addFavorite(String title) {
		Video video = videos.get(title);
		video.addFavorite();
	}

	public void addView(String title) {
		Video video = videos.get(title);
		video.addView();
	}

	public void addRating(String title, double rating, int season) {
		Video video = videos.get(title);
		video.addRating(rating, season);
	}

	public double getRatingOfVideo(String videoTitle) {
		if (videos.get(videoTitle) == null) {
			return -1;
		}
		return videos.get(videoTitle).getRating();
	}

	public List<Video> applyFilters(final String year, final String genre,
									final String videoType) {
		List<Video> filteredVideos = new ArrayList<>();

		for (Map.Entry<String, Video> entry : videos.entrySet()) {
			Video video = entry.getValue();

			if (!(videoType.equals(video.getVideoType()))) {
				continue;
			}

			if (year != null && !(String.valueOf(video.getYear()).equals(year))) {
				continue;
			}

			if (genre != null && !(video.getGenres().contains(genre))) {
				continue;
			}

			filteredVideos.add(video);
		}

		return filteredVideos;
	}

	public List<Video> getVideosSortByRating(final int numVideos,
											 final String sortType, final String year,
											 final String genre, final String videoType) {
		List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

		filteredVideos = filteredVideos.stream().filter(video -> video.getRating() > 0)
				.toList();

		Comparator<Video> videoRatingComp = Comparator
				.comparing(Video::getRating)
				.thenComparing(Video::getTitle);

		if (sortType.equals("desc")) {
			videoRatingComp = videoRatingComp.reversed();
		}

		return filteredVideos.stream()
				.sorted(videoRatingComp)
				.limit(numVideos)
				.collect(Collectors.toList());
	}

	public List<Video> getVideosSortedByNumFavorite(final int numVideos,
													final String sortType, final String year,
													final String genre, final String videoType) {
		List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

		filteredVideos = filteredVideos.stream().filter(video -> video.getNumFavorite() > 0)
				.toList();

		Comparator<Video> videoFavoriteComp = Comparator
				.comparing(Video::getNumFavorite)
				.thenComparing(Video::getTitle);

		if (sortType.equals("desc")) {
			videoFavoriteComp = videoFavoriteComp.reversed();
		}

		return filteredVideos.stream()
				.sorted(videoFavoriteComp)
				.limit(numVideos)
				.collect(Collectors.toList());
	}

	public List<Video> getVideosSortedByDuration(final int numVideos,
												 final String sortType, final String year,
												 final String genre, final String videoType) {
		List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

		Comparator<Video> videoDurationComp = Comparator
				.comparing(Video::getDuration)
				.thenComparing(Video::getTitle);

		if (sortType.equals("desc")) {
			videoDurationComp = videoDurationComp.reversed();
		}

		return filteredVideos.stream()
				.sorted(videoDurationComp)
				.limit(numVideos)
				.collect(Collectors.toList());
	}

	public List<Video> getVideosSortedByMostViewed(final int numVideos,
												   final String sortType, final String year,
												   final String genre, final String videoType) {
		List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

		filteredVideos = filteredVideos.stream().filter(video -> video.getNumViews() > 0)
				.toList();

		Comparator<Video> videoMostViewedComp = Comparator
				.comparing(Video::getNumViews)
				.thenComparing(Video::getTitle);

		if (sortType.equals("desc")) {
			videoMostViewedComp = videoMostViewedComp.reversed();
		}

		return filteredVideos.stream()
				.sorted(videoMostViewedComp)
				.limit(numVideos)
				.collect(Collectors.toList());
	}

	public static void resetDatabase() {
		VideosDatabase.getInstance().videos = new HashMap<>();
		VideosDatabase.getInstance().videoTitles = new ArrayList<>();
	}
}
