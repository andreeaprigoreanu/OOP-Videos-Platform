package database;

import actor.Actor;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ActorsDatabase {
    /**
     * the single instance of this class
     */
    private static ActorsDatabase instance = null;
    /**
     * list with actors
     */
    private ArrayList<Actor> actors;

    private ActorsDatabase() {
        actors = new ArrayList<>();
    }

    /**
     * returns the instance
     */
    public static ActorsDatabase getInstance() {
        if (instance == null) {
            instance = new ActorsDatabase();
        }

        return instance;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    /**
     * populates the database with the actors given as input
     * @param actors
     */
    public void populateDatabase(final List<ActorInputData> actors) {
        for (ActorInputData actor : actors) {
            this.actors.add(new Actor(actor.getName(),
                    actor.getCareerDescription(), actor.getFilmography(),
                    actor.getAwards()));
        }
    }

    /**
     * sorts the actors list by average rating og the movies they appear in
     * @param numActors
     * @param sortType
     * @param videosDatabase
     * @return list with actors
     */
    public List<Actor> getActorsSortedByAvgRating(final int numActors, final String sortType,
                                                  final VideosDatabase videosDatabase) {
        Comparator<Actor> actorAvgComp = Comparator
                .comparing((Actor actor) ->
                        actor.getAverageRating(videosDatabase))
                .thenComparing(Actor::getName);

        if (sortType.equals("desc")) {
            actorAvgComp = actorAvgComp.reversed();
        }

        List<Actor> actorsList = new ArrayList<>(actors);
        return actorsList.stream()
                .filter(actor -> actor.getAverageRating(videosDatabase) > 0)
                .sorted(actorAvgComp)
                .limit(numActors)
                .collect(Collectors.toList());
    }

    /**
     * returns a list with the actors that won the given awards, sorted by the number of total
     * awards
     * @param awards
     * @param sortType
     * @return list with actors
     */
    public List<Actor> getActorsSortedByAwards(final List<String> awards,
                                               final String sortType) {
        Comparator<Actor> actorAwardsComp = Comparator
                .comparing(Actor::getNumAwards)
                .thenComparing(Actor::getName);

        if (sortType.equals("desc")) {
            actorAwardsComp = actorAwardsComp.reversed();
        }

        List<Actor> actorList = new ArrayList<>(actors);
        return actorList.stream()
                .filter(actor -> actor.hasAwards(awards))
                .sorted(actorAwardsComp)
                .collect(Collectors.toList());
    }

    /**
     * returns a list with the actors that have the given keywords in descriptions, sorted by the
     * number of total awards
     * @param keywords
     * @param sortType
     * @return list with actors
     */
    public List<Actor> getActorsSortedByKeywords(final List<String> keywords,
                                                 final String sortType) {
        Comparator<Actor> actorKeywordsComp = Comparator
                .comparing(Actor::getName);

        if (sortType.equals("desc")) {
            actorKeywordsComp = actorKeywordsComp.reversed();
        }

        List<Actor> actorList = new ArrayList<>(actors);
        return actorList.stream()
                .filter(actor -> actor.hasKeywords(keywords))
                .sorted(actorKeywordsComp)
                .collect(Collectors.toList());
    }

    /**
     * empties the current list with actors
     */
    public static void resetDatabase() {
        ActorsDatabase.getInstance().actors = new ArrayList<>();
    }
}
