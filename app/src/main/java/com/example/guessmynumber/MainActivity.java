package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton[] vettorePulsanti = new ImageButton[4];
    public static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(MainActivity.this,R.raw.background_music);
        mp.setLooping(true);
        mp.start();

        /*
         * Assegnazione degli eventi al cliccare di ogni pulsante del menù
         * I primi 3 pulsanti apriranno la finestra di gioco
         *      L'activity è la medesima ma viene passato un diverso parametro della difficoltà
         */
        vettorePulsanti[0] = (ImageButton) findViewById(R.id.imageButton);
        vettorePulsanti[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityDiGioco(0);
            }
        });

        vettorePulsanti[1] = (ImageButton) findViewById(R.id.imageButton2);
        vettorePulsanti[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityDiGioco(1);
            }
        });

        vettorePulsanti[2] = (ImageButton) findViewById(R.id.imageButton3);
        vettorePulsanti[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityDiGioco(2);
            }
        });

        vettorePulsanti[3] = (ImageButton) findViewById(R.id.imageButton4);
        vettorePulsanti[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivityScore(); }
        });
    }

    private void openActivityDiGioco(int scelta){
        mp.stop();
        mp = MediaPlayer.create(MainActivity.this,R.raw.suono_tasto);
        mp.start();
        Intent i = new Intent( this, ActivityDiGioco.class);
        ActivityDiGioco.guardaScelta(scelta);
        startActivity(i);
    }

    private void openActivityScore(){
        Intent i = new Intent( this, ActivityPodio.class);
        startActivity(i);
    }
}