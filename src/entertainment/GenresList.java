package entertainment;

import database.VideosDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GenresList {
    /**
     * list with all the possible genres
     */
    private final List<String> genres;

    public GenresList() {
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

    /**
     * returns total number of views of the videos with the given genre
     * @param genreName
     * @param videosDatabase
     * @return number of views
     */
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

    /**
     * computes the popularity of all genres and sorts the list
     * @param videosDatabase
     * @return sorted genres list
     */
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
