package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class ActivityRecord extends AppCompatActivity {
    private ImageButton salva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);

        salva = (ImageButton) findViewById(R.id.imageButton5);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityScore();
            }
        });
    }

    private void openActivityScore(){
        Intent i = new Intent( this, MainActivity.class);
        startActivity(i);
    }
}