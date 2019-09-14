package org.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class TVShow
{
    private String showName;
    private Season nextSeason;

    public TVShow(String showName)
    {
        this.showName = showName;
    }

    public String getShowName()
    {
        return this.showName;
    }

    public Season getNextSeason()
    {
        return this.nextSeason;
    }

    public void addNextSeason(int seasonNumber, LocalDate releaseDate)
    {
        this.nextSeason = new Season(seasonNumber, releaseDate);
    }

    public void addNextSeason(int seasonNumber, String releaseDate)
    {
        this.nextSeason = new Season(seasonNumber, releaseDate);
    }

    @Override
    public String toString()
    {
        if(this.nextSeason == null)
            return "-Status unknown about next season of '"+this.showName+"'.";

        if(this.nextSeason.releaseDate == null){
            return "-Season "+this.nextSeason.number+" of '"+this.showName+"' is airing in "+
                    this.nextSeason.releasePeriod+"!";
        }

        if(this.nextSeason.releaseDate.isBefore(LocalDate.now())){
            return "-Season "+this.nextSeason.number+" of '"+this.showName+"' is already up!";
        }else{
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return "-Season "+this.nextSeason.number+" of '"+this.showName+"' is airing in "+
                    DAYS.between(LocalDate.now(),  this.nextSeason.releaseDate)+" days!" +
                    "\n At "+dateFormat.format(this.nextSeason.releaseDate);
        }
    }

    public static class Season
    {
        public int number;
        public LocalDate releaseDate;
        public String releasePeriod;

        Season(int number, LocalDate releaseDate)
        {
            this.number = number;
            this.releaseDate = releaseDate;
        }

        Season(int number, String releasePeriod)
        {
            this.number = number;
            this.releasePeriod = releasePeriod;
        }

        @Override
        public String toString()
        {
            return "Number: "+this.number+", Release Date: "+releaseDate+", Release Period: "+this.releasePeriod;
        }
    }

}
