package database;

import actor.Actor;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActorsDatabase {
	private static ActorsDatabase instance = null;
	private ArrayList<Actor> actors;

	private ActorsDatabase() {
		actors = new ArrayList<>();
	}

	public static ActorsDatabase getInstance() {
		if (instance == null) {
			instance = new ActorsDatabase();
		}

		return instance;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	public void addActors(List<ActorInputData> actors) {
		for(ActorInputData actor : actors) {
			this.actors.add(new Actor(actor.getName(),
					actor.getCareerDescription(), actor.getFilmography(),
					actor.getAwards()));
		}
	}

	public List<Actor> getActorsSortedByAvgRating(int numActors, String sortType,
													   VideosDatabase videosDatabase) {
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

	public List<Actor> getActorsSortedByKeywords(final List<String> keywords,
												 final String sortType) {
		Comparator<Actor> actorKeywordsComp = Comparator
				.comparing(Actor::getName)
				.thenComparing(getActors()::indexOf);

		if (sortType.equals("desc")) {
			actorKeywordsComp = actorKeywordsComp.reversed();
		}

		List<Actor> actorList = new ArrayList<>(actors);
		return actorList.stream()
				.filter(actor -> actor.hasKeywords(keywords))
				.sorted(actorKeywordsComp)
				.collect(Collectors.toList());
	}

	public static void resetDatabase() {
		ActorsDatabase.getInstance().actors = new ArrayList<>();
	}
}
