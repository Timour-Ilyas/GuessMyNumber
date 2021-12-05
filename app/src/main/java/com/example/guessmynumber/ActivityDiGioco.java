package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.graphics.Color;

public class ActivityDiGioco extends AppCompatActivity {
    public static String stato = "";
    //LinearLayout layout = findViewById(R.id.layoutDiGioco);

    public static void guardaScelta(String scelta){
        stato = scelta;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_di_gioco);

        /*
         * Cambio colore sfondo
         */
        switch (stato){
            case "Facile": {
               // layout.setBackgroundColor(Color.GREEN);
            }//Parentesi di chiusura case 0
            case "Normale": {
                //layout.setBackgroundColor(Color.YELLOW);
            }//Parentesi di chiusura case 1
            case "Difficile": {
                //layout.setBackgroundColor(Color.RED);
            }//Parentesi di chiusura case2
        }//Parentesi di chiusura switch
    }//Chiusura metodo onCreate
}