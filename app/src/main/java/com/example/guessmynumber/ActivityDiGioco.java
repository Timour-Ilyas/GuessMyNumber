package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.graphics.Color;

import com.example.guessmynumber.databinding.ActivityDiGiocoBinding;
import java.util.Random;

public class ActivityDiGioco extends AppCompatActivity {
    public static int stato = 0;
    private int contatore = 0;

    private Chronometer chronometer;
    private long tempoImpiegato;
    /*
     * Vettore di 12 pulsanti
     *      10 pulsanti per i numeri
     *      1 pulsante per confermare il numero inserito
     *      1 pulsante per tornare al menù principale
     */
    private ImageButton[] vettorePulsanti = new ImageButton[12];
    /*
     * Questa variabile è necessaria per collegare la classe Java al componente testo in cui compare
     * il numero digitato
     */
    ActivityDiGiocoBinding b;
    /*
     * Questa stringa avrà il contenuto digitato dall'utente e servirà per
     *      mostrare su schermo ciò che ha digitato l'utente
     *      comparare il contenuto con il numero che deve indovinare
     */
    private String numeriInseriti = "";
    //Generatore casuale di numeri
    private Random randomGenerator = new Random();
    //Variabile che prende il contenuto del numero generato casualmente
    private int numeroGeneratoCasualmente;

    public static void guardaScelta(int scelta){
        stato = scelta;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_di_gioco);

        inizioTempo();

        /*
         * Cambio canzone sottofondo
         */
        if(stato == 0){
            MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.vasco_rossi_un_senso);
        }else if (stato == 1){
            MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.platform_nine_ncs);
        }else{
            MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.gameplay_music_final);
        }
        MainActivity.mp.setLooping(true);
        MainActivity.mp.start();

        b = ActivityDiGiocoBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        //Assegnazione del numero generato casualmente nella creazione della pagina
        numeroGeneratoCasualmente = randomGenerator.nextInt(10000);

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
                if(numeriInseriti.length() > 0)
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

        vettorePulsanti[11] = (ImageButton) findViewById(R.id.imageButton6);
        vettorePulsanti[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ritornoAllaFinestraPrincipale();
            }
        });
    }//Chiusura metodo onCreate

    private void inserimentoNumero(String scelta){
        if(numeriInseriti.length() < 4) {//Limite di cifre inseribili nel numero
            numeriInseriti += scelta;

            b.campoPerLascritta.setTextSize(80);
            b.campoPerLascritta.setTextColor(Color.rgb(255,87,34));
            b.campoPerLascritta.setText(numeriInseriti);
            System.out.println("lol: " + chronometer.getBase());
        }
    }

    private void confermaNumeroInserito(){
        if(numeriInseriti.length() > 0) {//Prima si controlla che il numero sia stato inserito
            contatore++;
            b.textView.setText("Numero di tentativi: " + contatore);
            if (Integer.valueOf(numeriInseriti) > numeroGeneratoCasualmente) {
                /*
                 * Il numero inserito è maggiore del numero da indovinare
                 * Caso rosso: attivazione immagine rossa
                 */
                b.campoPerLascritta.setTextSize(40);
                b.campoPerLascritta.setTextColor(Color.RED);
                b.campoPerLascritta.setText("Troppo alto");
                numeriInseriti = "";
            } else if (Integer.valueOf(numeriInseriti) < numeroGeneratoCasualmente) {
                /*
                 * Il numero inserito è minore del numero da indovinare
                 * Caso verde: attivazione immagine verde
                 */
                b.campoPerLascritta.setTextSize(40);
                b.campoPerLascritta.setTextColor(Color.RED);
                b.campoPerLascritta.setText("Troppo basso");
                numeriInseriti = "";
            } else {
                /*
                 * GOOD ENDING: Numero indovinato
                 * Attivazione finestrella di vittoria
                 * Se è un record attivazione finestrella di record
                 */
                chronometer.stop();
                tempoImpiegato = chronometer.getBase();
                b.campoPerLascritta.setTextSize(40);
                b.campoPerLascritta.setTextColor(Color.GREEN);
                b.campoPerLascritta.setText("VITTORIA");
                b.imageButton21.setVisibility(View.INVISIBLE);
                MainActivity.mp.stop();
                MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.we_are_the_champions);
                MainActivity.mp.start();
                MainActivity.mp.setLooping(false);
                for(int i = 0; i < vettorePulsanti.length-1; i++)
                    vettorePulsanti[i].setVisibility(View.INVISIBLE);
                b.imageView7.setVisibility(View.VISIBLE);
            }
        }
    }//Chiusura del metodo di conferma numero

    private void ritornoAllaFinestraPrincipale(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void inizioTempo(){
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Tempo: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }
}