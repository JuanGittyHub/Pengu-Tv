package za.ac.cput.pengu_tv;

import java.io.Serializable;

public class AnimeDataEntity implements Serializable {
    private int id;
    private String title;
    private String description;
    private String total;
    private String ongoing;
    private long episodeAmount;
    private String genre;
    private double average;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOngoing() {
        return ongoing;
    }

    public void setOngoing(String ongoing) {
        this.ongoing = ongoing;
    }

    public Long getEpisodeAmount() {
        return episodeAmount;
    }

    public void setEpisodeAmount(Long episodeAmount) {
        this.episodeAmount = episodeAmount;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
