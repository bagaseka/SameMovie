package bagasekaz.projects.samemovie.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

