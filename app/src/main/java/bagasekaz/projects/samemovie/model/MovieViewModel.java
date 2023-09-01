package bagasekaz.projects.samemovie.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> movieLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<Movie>> getMovieLiveData() {
        return movieLiveData;
    }

    // Implement method to update itemsLiveData when data changes
    public void updateMovie(ArrayList<Movie> updatedItems) {
        movieLiveData.setValue(updatedItems);
    }

    public void deleteMovie(ArrayList<Movie> updatedItems) {
        movieLiveData.setValue(updatedItems);
    }
}



