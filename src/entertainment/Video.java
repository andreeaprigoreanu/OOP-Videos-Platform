package entertainment;

import java.util.ArrayList;

public abstract class Video {
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
    protected int duration = 0;
    /**
     * Number of occurrences in users' lists with favorite videos
     */
    private int numFavorite = 0;
    /**
     * Total number of views
     */
    private int numViews = 0;
    /**
     * Rating of a video
     */
    protected double rating = 0;

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

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return cast
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * @return genres
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @return value of numFavorites
     */
    public int getNumFavorite() {
        return numFavorite;
    }

    /**
     * @return number of views
     */
    public int getNumViews() {
        return numViews;
    }

    /**
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * increments number of occurrences in users' lists with favorite videos
     */
    public void addFavorite() {
        numFavorite++;
    }

    /**
     * incremets number of views
     */
    public void addView() {
        numViews++;
    }

    /**
     * adds views to a video
     * @param numViews
     */
    public void addViews(final int numViews) {
        this.numViews += numViews;
    }

    /**
     * adds new rating and computes the new average rating
     * @param rating
     * @param season
     */
    public abstract void addRating(double rating, int season);

    /**
     * @return video type
     */
    public abstract String getVideoType();


    /**
     * overrides toString method
     */
    @Override
    public String toString() {
        return title;
    }
}
