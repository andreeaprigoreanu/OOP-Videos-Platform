package main;

import action.Command;
import action.Query;
import action.Recommendation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import database.ActorsDatabase;
import database.UsersDatabase;
import database.VideosDatabase;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;

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

        // add actors to database
        ActorsDatabase actorsDatabase = ActorsDatabase.getInstance();
        actorsDatabase.populateDatabase(input.getActors());

        // add videos to database
        VideosDatabase videosDatabase = VideosDatabase.getInstance();
        videosDatabase.populateDatabase(input.getMovies(), input.getSerials());

        // add users to database
        UsersDatabase usersDatabase = UsersDatabase.getInstance();
        usersDatabase.populateDatabase(input.getUsers(), videosDatabase);

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
                    default:
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
                                default:
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
                                default:
                                    break;
                            }
                            break;
                    }
                } else {
                    switch (action.getType()) {
                        case "standard":
                            outputMessage = Recommendation.Standard
                                    .getStandardRec(action.getUsername(),
                                            usersDatabase, videosDatabase);
                            break;
                        case "best_unseen":
                            outputMessage = Recommendation.Standard
                                    .getBestUnseen(action.getUsername(),
                                            usersDatabase, videosDatabase);
                            break;
                        case "popular":
                            outputMessage = Recommendation.Premium
                                    .getPopular(action.getUsername(),
                                            usersDatabase, videosDatabase);
                            break;
                        case "favorite":
                            outputMessage = Recommendation.Premium
                                    .getFavorite(action.getUsername(),
                                            usersDatabase, videosDatabase);
                            break;
                        case "search":
                            outputMessage = Recommendation.Premium
                                    .getSearch(action.getUsername(),
                                            action.getGenre(), usersDatabase,
                                            videosDatabase);
                            break;
                        default:
                            break;
                    }
                }
            }

            arrayResult.add(fileWriter.writeFile(id, null, outputMessage));
        }

        // reset databases
        ActorsDatabase.resetDatabase();
        UsersDatabase.resetDatabase();
        VideosDatabase.resetDatabase();

        fileWriter.closeJSON(arrayResult);
    }
}
