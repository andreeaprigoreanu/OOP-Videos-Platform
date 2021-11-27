package database;

import entertainment.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenreDatabase {
	List<String> genres;

	public GenreDatabase() {
		genres = new ArrayList<>();
		genres.add("Action");
		genres.add("Adventure");
		genres.add("Animation");
		genres.add("Action & Adventure");
		genres.add("Comedy");
		genres.add("Crime");
		genres.add("Drama");
		genres.add("Fantasy");
		genres.add("Family");
		genres.add("History");
		genres.add("Horror");
		genres.add("Kids");
		genres.add("Mystery");
		genres.add("Romance");
		genres.add("Science Fiction");
		genres.add("Sci-Fi & Fantasy");
		genres.add("Tv Movie");
		genres.add("Thriller");
		genres.add("War");
		genres.add("Western");
	}

	public List<String> getGenres() {
		return genres;
	}

	public int getPopularity(final String genreName,
							 final VideosDatabase videosDatabase) {
		int popularity = 0;

		for (Map.Entry<String, Video> videoEntry : videosDatabase.getVideos()
				.entrySet()) {
			String videoTitle = videoEntry.getKey();
			Video video = videoEntry.getValue();
			if (video.getGenres().contains(genreName)) {
				popularity += videosDatabase.getVideos().get(videoTitle)
						.getNumViews();
			}
		}

		return popularity;
	}

	public List<String> sortByPopularity(final VideosDatabase videosDatabase) {
		Comparator<Object> genresComp = Comparator
				.comparing(genreName -> this.getPopularity(String
						.valueOf(genreName), videosDatabase))
				.reversed();

		return this.getGenres().stream()
				.sorted(genresComp)
				.collect(Collectors.toList());
	}
}
