package main;

import action.Command;
import action.Query;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import database.ActorsDatabase;
import database.UsersDatabase;
import database.VideosDatabase;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.*;
import org.json.simple.JSONArray;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
	/**
	 * for coding style
	 */
	private Main() {
	}

	/**
	 * Call the main checker and the coding style checker
	 * @param args from command line
	 * @throws IOException in case of exceptions to reading / writing
	 */
	public static void main(final String[] args) throws IOException {
		File directory = new File(Constants.TESTS_PATH);
		Path path = Paths.get(Constants.RESULT_PATH);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}

		File outputDirectory = new File(Constants.RESULT_PATH);

		Checker checker = new Checker();
		checker.deleteFiles(outputDirectory.listFiles());

		for (File file : Objects.requireNonNull(directory.listFiles())) {

			String filepath = Constants.OUT_PATH + file.getName();
			File out = new File(filepath);
			boolean isCreated = out.createNewFile();
			if (isCreated) {
				action(file.getAbsolutePath(), filepath);
			}
		}

		checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
		Checkstyle test = new Checkstyle();
		test.testCheckstyle();
	}

	/**
	 * @param filePath1 for input file
	 * @param filePath2 for output file
	 * @throws IOException in case of exceptions to reading / writing
	 */
	public static void action(final String filePath1,
							  final String filePath2) throws IOException {
		InputLoader inputLoader = new InputLoader(filePath1);
		Input input = inputLoader.readData();

		Writer fileWriter = new Writer(filePath2);
		JSONArray arrayResult = new JSONArray();

		//TODO add here the entry point to your implementation

		// add actors to database
		ActorsDatabase actorsDatabase = ActorsDatabase.getInstance();
		actorsDatabase.addActors(input.getActors());

		// add videos to database
		VideosDatabase videosDatabase = VideosDatabase.getInstance();
		// movies
		for (MovieInputData movieInputData : input.getMovies()) {
			Movie newMovie = new Movie(movieInputData.getTitle(), movieInputData.getYear(),
					movieInputData.getCast(), movieInputData.getGenres(),
					movieInputData.getDuration());
			videosDatabase.addVideo(newMovie.getTitle(),  newMovie);
		}
		// serials
		for (SerialInputData serialInputData : input.getSerials()) {
			Serial newSerial = new Serial(serialInputData.getTitle(),
					serialInputData.getYear(), serialInputData.getCast(),
					serialInputData.getGenres(),
					serialInputData.getNumberSeason(),
					serialInputData.getSeasons());
			videosDatabase.addVideo(newSerial.getTitle(), newSerial);
		}

		// add users to database
		UsersDatabase usersDatabase = UsersDatabase.getInstance();
		for (UserInputData userInputData : input.getUsers()) {
			User newUser = new User(userInputData.getUsername(),
					userInputData.getSubscriptionType(), userInputData.getHistory(),
					userInputData.getFavoriteMovies(),  videosDatabase);
			usersDatabase.addUser(newUser.getUsername(),  newUser);
		}

		List<ActionInputData> commandsData = input.getCommands();
		for (ActionInputData action : commandsData) {
			int id = action.getActionId();
			String actionType = action.getActionType();
			String outputMessage = null;

			if (actionType.equals("command")) {
				switch (action.getType()) {
					case "favorite":
						outputMessage = Command.favorite(action.getUsername(),
								action.getTitle(), usersDatabase, videosDatabase);
						break;
					case "view":
						outputMessage = Command.view(action.getUsername(),
								action.getTitle(), usersDatabase, videosDatabase);
						break;
					case "rating":
						outputMessage = Command.rateVideo(action.getUsername(),
								action.getTitle(), action.getGrade(),
								action.getSeasonNumber(), usersDatabase,
								videosDatabase);
						break;
				}
			} else {
				if (actionType.equals("query")) {
					switch (action.getObjectType()) {
						case "actors":
							switch (action.getCriteria()) {
								case "average":
									outputMessage = Query.ActorsQuery
										.sortByAverage(action.getNumber(),
												action.getSortType(),
												actorsDatabase, videosDatabase);
									break;
								case "awards":
									outputMessage = Query.ActorsQuery
											.sortByAwards(action.getFilters().get(3),
													action.getSortType(), actorsDatabase);
									break;
								case "filter_description":
									outputMessage = Query.ActorsQuery
											.sortByDescription(action.getFilters().get(2),
													action.getSortType(), actorsDatabase);
									break;
							}
							break;
						case "users":
							outputMessage = Query.UsersQuery
									.sortByNumRatings(action.getNumber(),
											action.getSortType(),
											usersDatabase);
							break;
						default:
							switch (action.getCriteria()) {
								case "ratings":
									outputMessage = Query.VideosQuery
											.sortByRating(action.getNumber(),
													action.getSortType(),
													action.getFilters().get(0).get(0),
													action.getFilters().get(1).get(0),
													action.getObjectType(),
													videosDatabase);
									break;
								case "favorite":
									outputMessage = Query.VideosQuery
											.sortByNumFavorite(action.getNumber(),
													action.getSortType(),
													action.getFilters().get(0).get(0),
													action.getFilters().get(1).get(0),
													action.getObjectType(),
													videosDatabase);
									break;
								case "longest":
									outputMessage = Query.VideosQuery
											.sortByDuration(action.getNumber(),
													action.getSortType(),
													action.getFilters().get(0).get(0),
													action.getFilters().get(1).get(0),
													action.getObjectType(),
													videosDatabase);
									break;
								case "most_viewed":
									outputMessage = Query.VideosQuery
											.sortByMostViewed(action.getNumber(),
													action.getSortType(),
													action.getFilters().get(0).get(0),
													action.getFilters().get(1).get(0),
													action.getObjectType(),
													videosDatabase);
									break;
							}
							break;
					}
				}
			}

			// JSONObject jsonObject = new JSONObject();
			// jsonObject.put("id", id);
			// jsonObject.put("message", outputMessage);
			// arrayResult.add(jsonObject);

			arrayResult.add(fileWriter.writeFile(id, null, outputMessage));
		}

		ActorsDatabase.resetDatabase();
		UsersDatabase.resetDatabase();
		VideosDatabase.resetDatabase();

		fileWriter.closeJSON(arrayResult);
	}
}
