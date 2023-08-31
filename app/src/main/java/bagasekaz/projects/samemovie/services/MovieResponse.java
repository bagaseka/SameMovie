package bagasekaz.projects.samemovie.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import bagasekaz.projects.samemovie.model.Movie;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movie;

    public List<Movie> getMovie() {
        return movie;
    }

    public void setLeagues(List<Movie> movie) {
        this.movie = movie;
    }
}
