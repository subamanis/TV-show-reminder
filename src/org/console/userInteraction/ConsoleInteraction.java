package org.console.userInteraction;

import org.console.logic.TVShow;
import org.console.logic.Utilities;

import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class ConsoleInteraction
{
    private static final int EXIT_OPTION = 0;
    private static final int PRINT_SHOWS_OPTION = 1;
    private static final int ADD_TV_SHOW_OPTION = 2;
    private static final int SPECIFY_SEASON_OPTION = 3;
    private static final int DELETE_OPTION = 4;

    List<TVShow> showList = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void run()
    {
        showList = Utilities.readFromFile();

        printShowsStatus();

        int menuOption;
        do {
            printMenu();

            menuOption = Utilities.getIntInputWithBounds("Select an option: ",
            "Error: please enter a valid menu option", EXIT_OPTION, DELETE_OPTION);

            System.out.println();
            if(menuOption == PRINT_SHOWS_OPTION){
                printShowsStatus();
            }else if(menuOption == ADD_TV_SHOW_OPTION){
                addTvShow();
            }else if(menuOption == SPECIFY_SEASON_OPTION){
                specifySeason();
            }else if(menuOption == DELETE_OPTION){
                deleteShow();
            }
        }while (menuOption != 0);
    }

    private void printMenu()
    {
        System.out.println();
        System.out.println(EXIT_OPTION +") To exit");
        System.out.println(PRINT_SHOWS_OPTION +") Print all TV-shows");
        System.out.println(ADD_TV_SHOW_OPTION +") Add a TV-show");
        System.out.println(SPECIFY_SEASON_OPTION +") Specify next season for a show");
        System.out.println(DELETE_OPTION +") Delete a show");
    }

    private void printShowsStatus()
    {
        if (showList.isEmpty())
        {
            System.out.println("No TV-shows saved yet!");
            return;
        }

        showList.sort((o1, o2) -> {
            if (o1.getNextSeason() == null || o1.getNextSeason().releaseDate == null) return -1;
            if (o2.getNextSeason() == null || o2.getNextSeason().releaseDate == null) return 1;

            return (int) DAYS.between(o1.getNextSeason().releaseDate, o2.getNextSeason().releaseDate);
        });

        System.out.println("Let me update you on the status of your favourite TV-shows:");
        for (TVShow tvs : showList)
        {
            System.out.println(tvs);
        }
    }

    private void addTvShow()
    {
        System.out.print("Name of the show:");
        String showName =  sc.nextLine();

        TVShow currentShow = new TVShow(showName);
        showList.add(currentShow);

        System.out.print("Type 'ok' to specify info about the next season: ");
        String response = sc.next();
        sc.nextLine();

        if(response.equalsIgnoreCase("ok")){
            Utilities.createSeason(currentShow);
        }
        System.out.println("\n"+currentShow.getNextSeason());
        System.out.println("Show added!");

        saveShows();
    }

    private void specifySeason()
    {
        if (showList.isEmpty())
        {
            System.out.println("No TV-shows saved yet!");
            return;
        }

        for (int i = 0; i < showList.size(); i++) {
            System.out.println(i+1+") "+showList.get(i).getShowName());
        }

        int chosenShowIndex = Utilities.getIntInputWithBounds("Choose a show: ",
                "Error: not valid show number", 1, showList.size());

        TVShow chosenShow = showList.get(chosenShowIndex-1);
        if(chosenShow.getNextSeason() == null){
            System.out.println("\nNo information about next season of "+chosenShow.getShowName());
        }else{
            System.out.println("\nNext season information of "+chosenShow.getShowName()+": ");
            System.out.println(chosenShow.getNextSeason());
        }

        Utilities.createSeason(chosenShow);
        System.out.println("\n"+chosenShow.getNextSeason());
        System.out.println("Season information updated!");

        saveShows();
    }

    private void deleteShow()
    {
        if (showList.isEmpty())
        {
            System.out.println("No TV-shows saved yet!");
            return;
        }

        for (int i = 0; i < showList.size(); i++) {
            System.out.println(i+1+") "+showList.get(i).getShowName());
        }

        int chosenShowIndex = Utilities.getIntInputWithBounds("Choose a show to delete: ",
                "Error: not valid show number", 1, showList.size());

        showList.remove(chosenShowIndex-1);
        System.out.println("Show removed!");

        saveShows();
    }

    private void saveShows()
    {
        String result = Utilities.saveToFile(showList);
        if(!result.isBlank()){
            System.out.println(result);
        }
    }
}
