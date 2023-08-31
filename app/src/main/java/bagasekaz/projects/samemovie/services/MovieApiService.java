package bagasekaz.projects.samemovie.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("movie?api_key=f7b67d9afdb3c971d4419fa4cb667fbf")
    Call<MovieResponse> getMovie();
}


