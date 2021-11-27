package entertainment;

import java.util.ArrayList;

public class Movie extends Video {
    /**
     * Sum of ratings given by users
     */
    private double sumOfRatings = 0;
    /**
     * Number of ratings given by users
     */
    private int numOfRatings = 0;

    public Movie(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    @Override
    public void addRating(double rating, int season) {
        if (this.rating == 0) {
            this.rating = rating;
            this.sumOfRatings = rating;
            this.numOfRatings++;
        } else {
            this.sumOfRatings += rating;
            this.numOfRatings++;
            this.rating = this.sumOfRatings / this.numOfRatings;
        }
    }

    @Override
    public String getVideoType() {
        return "movies";
    }
}
