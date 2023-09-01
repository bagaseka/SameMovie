package bagasekaz.projects.samemovie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bagasekaz.projects.samemovie.R;
import bagasekaz.projects.samemovie.model.Movie;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {

    private ArrayList<Movie> listMovie;

    public ListMovieAdapter(ArrayList<Movie> list) {
        this.listMovie = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_movie, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieAdapter.ListViewHolder holder, int position) {
        Movie movie = listMovie.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvDate.setText(movie.getDate());
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
