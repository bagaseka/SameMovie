package bagasekaz.projects.samemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BuildConfig;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bagasekaz.projects.samemovie.adapter.ListMovieAdapter;
import bagasekaz.projects.samemovie.databinding.ActivityMainBinding;
import bagasekaz.projects.samemovie.model.Movie;
import bagasekaz.projects.samemovie.services.ApiConfig;
import bagasekaz.projects.samemovie.services.MovieApiService;
import bagasekaz.projects.samemovie.services.MovieResponse;
import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ListMovieAdapter movieAdapter;
    private ArrayList<Movie> list = new ArrayList<>();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    private static MovieApiService apiService = ApiConfig.getClient().create(MovieApiService.class);

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        movieAdapter = new ListMovieAdapter(list);
        initRv();

        requestData();

    }

    private void requestData(){

        Call<MovieResponse> movieResponseCall = apiService.getMovie();
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NotNull Call<MovieResponse> call, @NotNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    if (movieResponse != null && movieResponse.getMovie() != null) {
                        list.addAll(movieResponse.getMovie());
                        movieAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("NetworkCall", "Empty Data");
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MovieResponse> call, @NotNull Throwable t) {
                Log.d("NetworkCall", "Failed Fetch getLeague()/Failure");
            }
        });

    }

    public void initRv() {
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMovie.setHasFixedSize(true);
        binding.rvMovie.setAdapter(movieAdapter);
    }

}