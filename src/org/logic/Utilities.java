package org.logic;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Utilities
{
    private static Scanner sc = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final String PATH_TO_FILE_DIRECTORY = "shows";
    public static final String PATH_TO_DATA_FILE = PATH_TO_FILE_DIRECTORY+"/showData.txt";

    /* Ensures an int input from the user between two bounds.
     *  All the necessary checks are made.*/
    public static int getIntInputWithBounds(String promptMessage, String errorMessage, int lowerBound, int upperBound)
    {
        int input = -1;
        while (true) {
            System.out.print(promptMessage);
            try {
                input = sc.nextInt();
            }catch (InputMismatchException e){
                sc.nextLine();
            }
            if(input >= lowerBound && input <= upperBound){
                break;
            }else{
                System.out.println(errorMessage);
            }
        }
        return input;
    }

    /* Ensures an int input from the user without restrictions */
    public static int getIntInputNoBounds(String promptMessage, String errorMessage)
    {
        int input = -1;
        while (true) {
            System.out.print(promptMessage);
            try {
                input = sc.nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println(errorMessage);
                sc.nextLine();
            }
        }
        return input;
    }

    /* Saves the data from showList of ConsoleInteraction.java.
     *  for every saved show, it saves the name, and next season's number and
     *  the release date/release period in separate lines. If the next season
     * hasn't been specified, it saves only the name and the word undefined at the next line
     *   */
    public static String saveToFile(List<TVShow> givenList)
    {
        if (givenList.isEmpty()){
            return "Nothing to save";
        }

        try(PrintWriter pwr = new PrintWriter(new BufferedWriter(new FileWriter(new File(PATH_TO_DATA_FILE))))){
            for(TVShow show : givenList)
            {
                pwr.println(show.getShowName());
                TVShow.Season season = show.getNextSeason();
                if(season == null){
                    pwr.println("undefined");
                }else{
                    pwr.println(season.number);
                    if(season.releaseDate == null){
                        pwr.println(season.releasePeriod);
                    }else{
                        pwr.println(season.releaseDate.format(formatter));
                    }
                }
                pwr.println();
            }
        }catch (IOException e){
            return "IOException while trying to save";
        }

        return "";
    }

    /* Reads the file this.PATH_TO_DATA_FILE and fills the showList List of ConsoleInteraction.java
    *  by creating TV-shows according to the data saved
    *  */
    public static List<TVShow> readFromFile()
    {
        File dir = new File(PATH_TO_FILE_DIRECTORY);
        if(!dir.exists() || !dir.isDirectory()){
            dir.mkdir();
        }

        if(!(new File(PATH_TO_DATA_FILE).isFile())){
            return new ArrayList<>();
        }

        List<TVShow> shows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(PATH_TO_DATA_FILE)))){
            String line;
            while ((line = br.readLine()) != null){
                if(!line.trim().equals("")){
                    String release = br.readLine();
                    TVShow show = new TVShow(line);
                    if(!release.equals("undefined")){
                        String date = (br.readLine()).trim();
                        if(isDate(date)){
                            show.addNextSeason(Integer.parseInt(release), LocalDate.parse(date, formatter));
                        }else{
                            show.addNextSeason(Integer.parseInt(release), date);
                        }
                    }
                    shows.add(show);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return shows;
    }

    /* Gets the season number and the release date/period from the user and
     *  adds them as a season to currentShow. The valid format for the date
     *  is: dd/MM/yyyy and if any other format is provided then it is saved
     *  in the releaseDate field of the season */
    public static void createSeason(TVShow currentShow)
    {
        int seasonNum = getIntInputNoBounds("Number of the next season: ",
                "Error: Number of next season is not a number");

        System.out.print("Date(e.g. 24-03-2019) or period of season release(e.g. summer 2020): ");
        sc.nextLine();
        String release = sc.nextLine();

        try {
            LocalDate date = LocalDate.parse(release, formatter);
            currentShow.addNextSeason(seasonNum, date);
        }catch (DateTimeParseException e){
            currentShow.addNextSeason(seasonNum, release);
        }
    }

    public static boolean isDate(String s)
    {
        try{
            LocalDate.parse(s, formatter);
            return true;
        }catch (DateTimeParseException e){
            return false;
        }
    }
}
