package bagasekaz.projects.samemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import bagasekaz.projects.samemovie.helper.DatabaseHelper;
import bagasekaz.projects.samemovie.helper.NetworkHelper;
import bagasekaz.projects.samemovie.model.Movie;

public class SplashScreen extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!NetworkHelper.isInternetConnected(SplashScreen.this) && !checkDataLocal()) {
            showBottomSheetError();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 3000);

    }

    private boolean checkDataLocal(){
        boolean check = false;
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllMovies();
        if (cursor == null) {
            check = false;
        }
        return check;
    }

    private void showBottomSheetError() {
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_feedback, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


}