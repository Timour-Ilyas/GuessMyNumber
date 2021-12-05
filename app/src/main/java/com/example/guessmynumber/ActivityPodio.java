package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.example.guessmynumber.databinding.ActivityPodioBinding;

public class ActivityPodio extends AppCompatActivity {
    /*
     * Vettore di 11 pulsanti
     *      10 pulsanti per i numeri
     *      1 pulsante per confermare il numero inserito
     */
    private ImageButton[] vettorePulsanti = new ImageButton[11];
    ActivityPodioBinding b;
    private String numeriInseriti = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_podio);

        vettorePulsanti[0] = (ImageButton) findViewById(R.id.pulsante1);
        vettorePulsanti[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("1");
            }
        });

        vettorePulsanti[1] = (ImageButton) findViewById(R.id.pulsante2);
        vettorePulsanti[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("2");
            }
        });

        vettorePulsanti[2] = (ImageButton) findViewById(R.id.pulsante3);
        vettorePulsanti[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("3");
            }
        });

        vettorePulsanti[3] = (ImageButton) findViewById(R.id.pulsante4);
        vettorePulsanti[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("4");
            }
        });

        vettorePulsanti[4] = (ImageButton) findViewById(R.id.pulsante5);
        vettorePulsanti[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("5");
            }
        });

        vettorePulsanti[5] = (ImageButton) findViewById(R.id.pulsante6);
        vettorePulsanti[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("6");
            }
        });

        vettorePulsanti[6] = (ImageButton) findViewById(R.id.pulsante7);
        vettorePulsanti[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("7");
            }
        });

        vettorePulsanti[7] = (ImageButton) findViewById(R.id.pulsante8);
        vettorePulsanti[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("8");
            }
        });

        vettorePulsanti[8] = (ImageButton) findViewById(R.id.pulsante9);
        vettorePulsanti[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("9");
            }
        });

        vettorePulsanti[9] = (ImageButton) findViewById(R.id.pulsante0);
        vettorePulsanti[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserimentoNumero("0");

            }
        });

        vettorePulsanti[10] = (ImageButton) findViewById(R.id.imageButton21);
        vettorePulsanti[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermaNumeroInserito();
            }
        });
    }

    private void inserimentoNumero(String scelta){
        b = ActivityPodioBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        if(numeriInseriti.length() < 4)
            numeriInseriti += scelta;
        b.campoPerLascritta.setText(numeriInseriti);
        System.out.println(numeriInseriti);
    }

    private void confermaNumeroInserito(){

    }
}