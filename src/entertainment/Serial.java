package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Serial extends Video {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    /**
     * Constructor with parameters
     */
    public Serial(final String title, final int year,
                  final ArrayList<String> cast,
                  final ArrayList<String> genres, final int numberOfSeasons,
                  final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.calculateDuration();
        this.calculateRating();
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    private void calculateDuration() {
        for (Season season : seasons) {
            duration += season.getDuration();
        }
    }

    private void calculateRating() {
        for (Season season : seasons) {
            double seasonRating = 0;
            for (Double ratingIt : season.getRatings()) {
                seasonRating += ratingIt;
            }

            if (season.getRatings().size() != 0) {
                seasonRating = seasonRating / season.getRatings().size();
                rating += seasonRating;
            }
        }

        rating = rating / numberOfSeasons;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void addRating(final double rating, final int season) {
        int indexSeason = season - 1;
        List<Double> ratings = seasons.get(indexSeason).getRatings();
        ratings.add(rating);

        this.calculateRating();
    }

    @Override
    public String getVideoType() {
        return "shows";
    }
}
