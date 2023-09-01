package bagasekaz.projects.samemovie.services;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import bagasekaz.projects.samemovie.model.Movie;

public class MovieResponse {
    @SerializedName("results")
    private ArrayList<Movie> movie;

    public ArrayList<Movie> getMovie() {
        return movie;
    }

    public void setLeagues(ArrayList<Movie> movie) {
        this.movie = movie;
    }
}
