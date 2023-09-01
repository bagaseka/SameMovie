package bagasekaz.projects.samemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bagasekaz.projects.samemovie.adapter.ListMovieAdapter;
import bagasekaz.projects.samemovie.databinding.ActivityMainBinding;
import bagasekaz.projects.samemovie.helper.DatabaseHelper;
import bagasekaz.projects.samemovie.model.Movie;
//import bagasekaz.projects.samemovie.room.AppDatabase;
//import bagasekaz.projects.samemovie.room.MovieData;
//import bagasekaz.projects.samemovie.room.ViewModel;
import bagasekaz.projects.samemovie.services.ApiConfig;
import bagasekaz.projects.samemovie.services.MovieApiService;
import bagasekaz.projects.samemovie.services.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListMovieAdapter movieAdapter;
    private ArrayList<Movie> list = new ArrayList<>();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    private static MovieApiService apiService = ApiConfig.getClient().create(MovieApiService.class);
    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        movieAdapter = new ListMovieAdapter(list);
        initRv();

        requestData();
        readLocalData();
    }
    private void readLocalData(){
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllMovies();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RELEASE));
                Movie movie = new Movie(id,title,releaseDate);
                list.add(movie);
            }
        }
    }
    private void requestData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<MovieResponse> movieResponseCall = apiService.getMovie();
                movieResponseCall.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse movieResponse = response.body();
                            if (movieResponse != null && movieResponse.getMovie() != null) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        moveDataIntoLocal(movieResponse.getMovie());
                                    }
                                });
                            } else {
                                Log.d("Data", "Empty Data");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                        Log.d("NetworkCall", "Failed Fetch getMovie()/Failure");
                    }
                });
            }
        });

        thread.start();
    }

    private void moveDataIntoLocal(ArrayList<Movie> movies){
        executor.execute(() -> {
            try {
                for (Movie movie : movies){
                    Thread.sleep(60000);
                    handler.post(() -> {
                        String id = movie.getId();
                        String title = movie.getTitle();
                        String releaseDate = movie.getDate();
                        databaseHelper = new DatabaseHelper(this);
                        databaseHelper.addMovie(id,title, releaseDate);
                        Snackbar.make(binding.layout, "New movie " + movie.getTitle() + " is available", Snackbar.LENGTH_SHORT).show();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void initRv() {
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMovie.setHasFixedSize(true);
        binding.rvMovie.setAdapter(movieAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}