package bagasekaz.projects.samemovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import bagasekaz.projects.samemovie.helper.NetworkHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!NetworkHelper.isInternetConnected(SplashScreen.this)) {
            showBottomSheetError();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(mainIntent);
//                finish();
            }
        }, 3000);


    }

    private void showBottomSheetError() {
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_feedback, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

}