package entertainment;

import java.util.ArrayList;

public class Video {
    /**
     * Video's title
     */
    private final String title;
    /**
     * The year the video was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    /**
     * Duration if a video
     */
    int duration = 0;
    /**
     * Number of times the video is in lists with favorite videos
     */
    private int numFavorite = 0;
    /**
     * Total number of views
     */
    private int numViews = 0;
    /**
     * Rating of a video
     */
    double rating = 0;

    /**
     * Constructor with parameters
     */
    public Video(final String title, final int year,
                 final ArrayList<String> cast,
                 final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getNumFavorite() {
        return numFavorite;
    }

    public int getNumViews() {
        return numViews;
    }

    public double getRating() {
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public void addFavorite() {
        numFavorite++;
    }

    public void addView() {
        numViews++;
    }

    public void addViews(int numViews) {
        this.numViews += numViews;
    }

    public void addRating(double rating, int season) {
    }

    public String getVideoType() {
        return null;
    }

    @Override
    public String toString() {
        return title;
    }
}
