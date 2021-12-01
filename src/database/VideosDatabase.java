package database;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class VideosDatabase {
    /**
     * the single instance of this class
     */
    private static VideosDatabase instance = null;
    /**
     * map with videos
     */
    private Map<String, Video> videos;
    /**
     * list with video titles
     */
    private List<String> videoTitles;

    private VideosDatabase() {
        videos = new HashMap<>();
        videoTitles = new ArrayList<>();
    }

    /**
     * @return instance
     */
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

    /**
     * adds video to database
     * @param title
     * @param video
     */
    public void addVideo(final String title, final Video video) {
        videos.put(title, video);
        videoTitles.add(title);
    }

    /**
     * populates the database with the movies and serials given as input
     * @param movies
     * @param serials
     */
    public void populateDatabase(final List<MovieInputData> movies,
                                 final List<SerialInputData> serials) {
        // add movies
        for (MovieInputData movieInputData : movies) {
            Movie newMovie = new Movie(movieInputData.getTitle(), movieInputData.getYear(),
                    movieInputData.getCast(), movieInputData.getGenres(),
                    movieInputData.getDuration());
            this.addVideo(newMovie.getTitle(),  newMovie);
        }

        // add serials
        for (SerialInputData serialInputData : serials) {
            Serial newSerial = new Serial(serialInputData.getTitle(),
                    serialInputData.getYear(), serialInputData.getCast(),
                    serialInputData.getGenres(),
                    serialInputData.getNumberSeason(),
                    serialInputData.getSeasons());
            this.addVideo(newSerial.getTitle(), newSerial);
        }
    }

    /**
     * updates the number of occurrences in users' favorite list of a video
     * @param title
     */
    public void addFavorite(final String title) {
        Video video = videos.get(title);
        video.addFavorite();
    }

    /**
     * updates the number views of a video
     * @param title
     */
    public void addView(final String title) {
        Video video = videos.get(title);
        video.addView();
    }

    /**
     * updates the rating of a video
     * @param title
     * @param rating
     * @param season
     */
    public void addRating(final String title, final double rating, final int season) {
        Video video = videos.get(title);
        video.addRating(rating, season);
    }

    /**
     * @param videoTitle
     * @return rating
     */
    public double getRatingOfVideo(final String videoTitle) {
        if (videos.get(videoTitle) == null) {
            return -1;
        }
        return videos.get(videoTitle).getRating();
    }

    /**
     * @param year
     * @param genre
     * @param videoType
     * @return list of videos that have the given year, genre and videoType
     */
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

    /**
     * returns a list with videos sorted by average rating
     * @param numVideos
     * @param sortType
     * @param year
     * @param genre
     * @param videoType
     * @return list of videos
     */
    public List<Video> getVideosSortByRating(final int numVideos,
                                             final String sortType, final String year,
                                             final String genre, final String videoType) {
        List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

        Comparator<Video> videoRatingComp = Comparator
                .comparing(Video::getRating)
                .thenComparing(Video::getTitle);

        if (sortType.equals("desc")) {
            videoRatingComp = videoRatingComp.reversed();
        }

        return filteredVideos.stream()
                .filter(video -> video.getRating() > 0)
                .sorted(videoRatingComp)
                .limit(numVideos)
                .collect(Collectors.toList());
    }

    /**
     * returns a list with videos sorted by the number of occurrences in users' favorite list
     * @param numVideos
     * @param sortType
     * @param year
     * @param genre
     * @param videoType
     * @return list of videos
     */
    public List<Video> getVideosSortedByNumFavorite(final int numVideos,
                                                    final String sortType, final String year,
                                                    final String genre, final String videoType) {
        List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

        Comparator<Video> videoFavoriteComp = Comparator
                .comparing(Video::getNumFavorite)
                .thenComparing(Video::getTitle);

        if (sortType.equals("desc")) {
            videoFavoriteComp = videoFavoriteComp.reversed();
        }

        return filteredVideos.stream()
                .filter(video -> video.getNumFavorite() > 0)
                .sorted(videoFavoriteComp)
                .limit(numVideos)
                .collect(Collectors.toList());
    }

    /**
     * returns a list with videos sorted by duration
     * @param numVideos
     * @param sortType
     * @param year
     * @param genre
     * @param videoType
     * @return list of videos
     */
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

    /**
     * returns a list with videos sorted by number of views
     * @param numVideos
     * @param sortType
     * @param year
     * @param genre
     * @param videoType
     * @return list of videos
     */
    public List<Video> getVideosSortedByMostViewed(final int numVideos,
                                                   final String sortType, final String year,
                                                   final String genre, final String videoType) {
        List<Video> filteredVideos = this.applyFilters(year, genre, videoType);

        Comparator<Video> videoMostViewedComp = Comparator
                .comparing(Video::getNumViews)
                .thenComparing(Video::getTitle);

        if (sortType.equals("desc")) {
            videoMostViewedComp = videoMostViewedComp.reversed();
        }

        return filteredVideos.stream()
                .filter(video -> video.getNumViews() > 0)
                .sorted(videoMostViewedComp)
                .limit(numVideos)
                .collect(Collectors.toList());
    }

    /**
     * empties the current map of videos and list of video titles
     */
    public static void resetDatabase() {
        VideosDatabase.getInstance().videos = new HashMap<>();
        VideosDatabase.getInstance().videoTitles = new ArrayList<>();
    }
}
