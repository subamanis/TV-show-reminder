package org.gui.logic;

import java.time.LocalDate;
import java.util.Objects;

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
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TVShow tvShow = (TVShow) o;
        return showName.equals(tvShow.showName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(showName);
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

