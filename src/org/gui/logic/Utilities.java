package org.gui.logic;

import org.gui.ui.ScreenPanelTwo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Utilities
{
    private static final String[] formatterPatterns = {"dd-MM-uuuu", "MM-dd-uuuu", "uuuu-MM-dd"};
    private static final String[] sortingOrderOptions = {"Ascending", "Descending"};
    private static final int[] sortingOrders = {-1, 1};

    private static final String defaultFormatterPattern = formatterPatterns[0];
    private static final int defaultSortingOrder = sortingOrders[0];

    private static String currentFormatterPattern = defaultFormatterPattern;
    private static int currentSortingOrder = defaultSortingOrder;

    private static final DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern(formatterPatterns[0]).withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern(formatterPatterns[1]).withResolverStyle(ResolverStyle.STRICT),
            DateTimeFormatter.ofPattern(formatterPatterns[2]).withResolverStyle(ResolverStyle.STRICT)
    };
    private static final DateTimeFormatter defaultFormatter = formatters[0];
    private static DateTimeFormatter currentFormatter = defaultFormatter;

    public static final String PATH_TO_FILE_DIRECTORY = "shows";
    public static final String PATH_TO_DATA_FILE = PATH_TO_FILE_DIRECTORY+"/showData.txt";
    public static final String PATH_TO_PREFS_FILE = PATH_TO_FILE_DIRECTORY+"/prefs.txt";


    /* Saves the data of a list of TVShow objects to a file
     *  for every saved show, it saves the name, and next season's number and
     *  the release date/release period in separate lines. If the next season
     * hasn't been specified, it saves only the name and the word undefined at the next line
     *   */
    public static void saveToFile(List<TVShow> givenList)
    {
        if (givenList.isEmpty()){
            return;
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
                        pwr.println(season.releaseDate.format(defaultFormatter));
                    }
                }
                pwr.println();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void savePrefs()
    {
        try(PrintWriter pwr = new PrintWriter(new BufferedWriter(new FileWriter(new File(PATH_TO_PREFS_FILE))))){
            pwr.println(currentFormatterPattern);
            pwr.print(currentSortingOrder);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /* Reads the file this.PATH_TO_DATA_FILE and fills the showList List of ConsoleInteraction.java
     *  by creating TV-shows according to the data saved
     *  */
    public static List<TVShow> readFromFile()
    {
        File dir = new File(PATH_TO_FILE_DIRECTORY);
        if(!dir.exists() || !dir.isDirectory()){
            dir.mkdir();
            return new ArrayList<>();
        }

        readPrefs();

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
                        if(isDateOfDefaultPattern(date)){
                            show.addNextSeason(Integer.parseInt(release),
                                    LocalDate.parse(toggleCurrentAndDefaultFormatterPattern(date), currentFormatter));
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

    private static void readPrefs()
    {
        if(!new File(PATH_TO_PREFS_FILE).isFile()){
            return;
        }

        if((new File(PATH_TO_PREFS_FILE).isFile())){
            try (BufferedReader br = new BufferedReader(new FileReader(new File(PATH_TO_PREFS_FILE)))){
                String line = br.readLine();
                if(line != null){
                    setFormatterPattern(line.trim());
                }
                line = br.readLine();
                if(line != null){
                    setSortingOrder(Integer.parseInt(line.trim()));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void setFormatterPattern(String pattern)
    {
        int index=-1;
        for (int i = 0; i < formatterPatterns.length; i++) {
            if(formatterPatterns[i].equals(pattern)){
                index = i;
                break;
            }
        }
        if(index == -1){
            return;
        }

        currentFormatterPattern = pattern;
        currentFormatter = formatters[index];
    }

    public static DateTimeFormatter getCurrentFormatter()
    {
        return currentFormatter;
    }

    public static String getCurrentFormatterPattern()
    {
        return currentFormatterPattern;
    }

    private static String toggleCurrentAndDefaultFormatterPattern(String pattern)
    {
        if(currentFormatter == formatters[1]){
            return toggle_MM_dd_uuuu_AndDefault(pattern);
        }else if(currentFormatter == formatters[2]){
            return toggle_uuuu_MM_dd_AndDefault(pattern);
        }else{
            return pattern;
        }
    }

    private static String toggle_uuuu_MM_dd_AndDefault(String pattern)
    {
        String[] elements = pattern.split("-");
        return elements[2]+"-"+elements[1]+"-"+elements[0];
    }

    private static String toggle_MM_dd_uuuu_AndDefault(String pattern)
    {
        String[] elements = pattern.split("-");
        return elements[1]+"-"+elements[0]+"-"+elements[2];
    }

    public static void setSortingOrder(String order)
    {
        int index = -1;
        for (int i = 0; i < sortingOrderOptions.length; i++) {
            if (sortingOrderOptions[i].equals(order)) {
                index = i;
            }
        }
        if(index == -1){
            currentSortingOrder = defaultSortingOrder;
        }else{
            currentSortingOrder = sortingOrders[index];
        }
    }

    public static String getSelectedSortingOrderOption()
    {
        int index = 0;
        for (int i = 0; i < sortingOrders.length; i++) {
            if(sortingOrders[i] == currentSortingOrder){
                index = i;
                break;
            }
        }
        return sortingOrderOptions[index];
    }

    private static void setSortingOrder(int order){
        for(int i : sortingOrders){
            if(i == order){
                currentSortingOrder = order;
                return;
            }
        }
        currentSortingOrder = defaultSortingOrder;
    }

    @SuppressWarnings("unchecked")
    public static Vector[] getVectorArrayFromList(List<TVShow> showList)
    {
        showList.sort((o1, o2) -> {
            if (o1.getNextSeason() == null || o1.getNextSeason().releaseDate == null) return -currentSortingOrder;
            if (o2.getNextSeason() == null || o2.getNextSeason().releaseDate == null) return currentSortingOrder;

            return (int) DAYS.between(o1.getNextSeason().releaseDate, o2.getNextSeason().releaseDate) * currentSortingOrder;
        });

        int showsCount = showList.size();
        Vector[] tableData = new Vector[showsCount];

        for (int i = 0; i < showsCount; i++) {
            TVShow currentShow = showList.get(i);

            tableData[i] = new Vector();
            tableData[i].add("     "+currentShow.getShowName());
            tableData[i].add("     "+currentShow.getNextSeason().number);

            String releasePeriod = currentShow.getNextSeason().releasePeriod;

            if(releasePeriod == null){
                tableData[i].add("     "+getDateAndRemainingDays(currentShow.getNextSeason().releaseDate));
            }else{
                tableData[i].add("     "+releasePeriod);
            }
        }

        return tableData;
    }

    public static boolean isDate(String s)
    {
        try{
            LocalDate.parse(s, currentFormatter);
            return true;
        }catch (DateTimeParseException e){
            return false;
        }
    }

    private static boolean isDateOfDefaultPattern(String s)
    {
        try{
            LocalDate.parse(s, defaultFormatter);
            return true;
        }catch (DateTimeParseException e){
            return false;
        }
    }

    public static LocalDate getDateFromString(String s)
    {
        try{
            return LocalDate.parse(s, currentFormatter);
        }catch (DateTimeParseException e){
            return null;
        }
    }

    public static String getDateAndRemainingDays(LocalDate date)
    {
        long daysBetween = DAYS.between(LocalDate.now(),  date);
        if(daysBetween<0){
            daysBetween = 0;
        }
        return date.format(currentFormatter)+"  ("+daysBetween+" days)";
    }

    public static boolean checkForDuplicates(List<TVShow> showList)
    {
        Set<TVShow> showSet = new HashSet<>(showList);
        return showSet.size() < showList.size();
    }

    public static boolean checkIfShowNameExistsExcludingIndex(int indexToExclude, String name, List<TVShow> showList)
    {
        for (int i = 0; i < showList.size(); i++) {
            if(showList.get(i).getShowName().equals(name) && i != indexToExclude){
                return true;
            }
        }
        return false;
    }

    public static void removeByName(String name, List<TVShow> showList)
    {
        int indexToBeRemoved = 0;
        for (int i = 0; i < showList.size(); i++) {
            if(showList.get(i).getShowName().equals(name)){
                indexToBeRemoved = i;
                break;
            }
        }
        showList.remove(indexToBeRemoved);
    }

    public static String getByName(String name, List<TVShow> showList)
    {
        TVShow wantedShow = null;
        for (TVShow tvShow : showList) {
            if (tvShow.getShowName().equals(name)) {
                wantedShow = tvShow;
                break;
            }
        }
        String releasePeriod = wantedShow.getNextSeason().releasePeriod;
        if(releasePeriod == null){
            return wantedShow.getNextSeason().releaseDate.format(currentFormatter);
        }
        return releasePeriod;
    }
}

