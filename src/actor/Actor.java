package actor;

import database.VideosDatabase;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Actor {
    /**
     * actor name
     */
    private final String name;
    /**
     * description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private final ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    /**
     * @return number of awards
     */
    public int getNumAwards() {
        int numAwards = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            numAwards += entry.getValue();
        }

        return numAwards;
    }

    /**
     * iterates through the videos list an actor appears in and computes their average rating
     * @param videosDatabase
     * @return average rating
     */
    public double getAverageRating(final VideosDatabase videosDatabase) {
        double sumRatings = 0;
        int numRatings = 0;

        for (String videoTitle : filmography) {
            double videoRating = videosDatabase.getRatingOfVideo(videoTitle);
            if (videoRating > 0) {
                sumRatings += videoRating;
                numRatings++;
            }
        }

        if (numRatings == 0) {
            return 0;
        } else {
            return sumRatings / numRatings;
        }
    }

    /**
     * checks if an actor won the awards given in a list
     * @param awardsList
     * @return true or false
     */
    public boolean hasAwards(final List<String> awardsList) {
        for (String awardName : awardsList) {
            if (!(awards.containsKey(Utils.stringToAwards(awardName)))) {
                return false;
            }
        }

        return true;
    }

    /**
     * checks if some keywords appear in the description of an actor
     * @param keywords
     * @return true or false
     */
    public boolean hasKeywords(final List<String> keywords) {
        for (String keyword : keywords) {
            // the keyword is searched using regex
            Pattern pattern = Pattern.compile("[ -.,!?']" + keyword + "[ -.,!?']",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(careerDescription);

            if (!(matcher.find())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
