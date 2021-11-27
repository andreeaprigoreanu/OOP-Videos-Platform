package actor;

import database.VideosDatabase;
import entertainment.Video;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public Actor(String name, String careerDescription,
                 ArrayList<String> filmography,
                 Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setAwards(Map<ActorsAwards, Integer> awards) {
        this.awards = awards;
    }

    public int getNumAwards() {
        return awards.size();
    }

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

    public boolean hasAwards(final List<String> awardsList) {
        for (String awardName : awardsList) {
            ActorsAwards award = Utils.stringToAwards(awardName);

            if (!(this.getAwards().containsKey(award))) {
                return false;
            }
        }

        return true;
    }

    public boolean hasKeywords(final List<String> keywords) {
        for (String keyword : keywords) {
            String regex = "[ -]" + keyword + "[ -.,!?']";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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

    public String showActor(VideosDatabase videosDatabase) {
        return name + " " + this.getAverageRating(videosDatabase);
    }

}
