package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.graphics.Color;
import com.example.guessmynumber.databinding.ActivityDiGiocoBinding;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityDiGioco extends AppCompatActivity {
    public static int stato = 1;
    public static int contatore;
    public static double tempoImpiegato;

    private int massimoInserimento = 0;
    private int numeroMassimoGenerabile = 0;

    //private TextView timerText;
    private Timer timer;
    private TimerTask timerTask;

    /*
     * Vettore di 12 pulsanti
     *      10 pulsanti per i numeri
     *      2 pulsanti per cancellare i valori inseriti
     *      1 pulsante per confermare il numero inserito
     *      1 pulsante per tornare al menù principale
     */
    private final ImageButton[] vettorePulsanti = new ImageButton[14];
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
    private final Random randomGenerator = new Random();
    //Variabile che prende il contenuto del numero generato casualmente
    private int numeroGeneratoCasualmente;

    private boolean record = false;

    public static void guardaScelta(int scelta){
        stato = scelta;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_di_gioco);

        b = ActivityDiGiocoBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        //timerText = (TextView) findViewById(R.id.cronometroLabel);
        timer = new Timer();
        tempoImpiegato = 0;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tempoImpiegato++;
                        b.cronometroLabel.setText("Tempo: " + presaDiTempo());
                        //timerText.setText("Tempo: " + presaDiTempo()); <-- Questo è un caso in cui il setText non funziona
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0,1000);

        /*
         * Cambio canzone sottofondo
         *      Generazione del numero in base alla difficoltà
         *      Impostazione del massimo numero di caratteri inseribili
         */
        if(stato == 1)
            MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.vasco_rossi_un_senso);
        else if (stato == 2)
            MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.platform_nine_ncs);
        else
            MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.gameplay_music_final);
        massimoInserimento = stato+1;

        numeroMassimoGenerabile = (int) Math.pow(10, massimoInserimento);
        numeroGeneratoCasualmente = randomGenerator.nextInt(numeroMassimoGenerabile);

        MainActivity.mp.setLooping(true);
        MainActivity.mp.start();

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

        vettorePulsanti[10] = (ImageButton) findViewById(R.id.pulsanteCancella);
        vettorePulsanti[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {//Try catch per far sì che almeno 1 numero debba essere stato inserito
                    numeriInseriti = numeriInseriti.substring(0, numeriInseriti.length() - 1);
                }catch (StringIndexOutOfBoundsException e){}
                b.campoPerLascritta.setText(numeriInseriti);
            }
        });

        vettorePulsanti[11] = (ImageButton) findViewById(R.id.tastoCancellaTutto);
        vettorePulsanti[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeriInseriti = "";
                b.campoPerLascritta.setText("");
            }
        });

        vettorePulsanti[12] = (ImageButton) findViewById(R.id.imageButton21);
        vettorePulsanti[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confermaNumeroInserito();
            }
        });

        vettorePulsanti[13] = (ImageButton) findViewById(R.id.imageButton6);
        vettorePulsanti[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ritornoAllaFinestraPrincipale();
            }
        });
        contatore = 0;//Serve per far sì che in più partite il valore si azzeri
    }//Chiusura metodo onCreate

    private void inserimentoNumero(String scelta){
        if(numeriInseriti.length() <= massimoInserimento) {//Limite di cifre inseribili nel numero
            numeriInseriti += scelta;

            b.campoPerLascritta.setTextSize(80);
            b.campoPerLascritta.setTextColor(Color.rgb(255,87,34));
            b.campoPerLascritta.setText(numeriInseriti);
        }
        //Controllo che non sia inserito un numero più grande di quello inseribile
        try{
            if(Integer.valueOf(numeriInseriti) > numeroMassimoGenerabile) {
                numeriInseriti = String.valueOf(Math.pow(10, massimoInserimento)).substring(0, numeriInseriti.length());
                b.campoPerLascritta.setText(numeriInseriti);
            }
        }catch (NumberFormatException e){ }//Eccezione nel caso il numero sia vuoto
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
                timerTask.cancel();
                MainActivity.mp.stop();
                MainActivity.mp = MediaPlayer.create(ActivityDiGioco.this,R.raw.we_are_the_champions);
                MainActivity.mp.start();
                MainActivity.mp.setLooping(false);

                //Controllo per capire se andare nella finestra record o no
                FileInputStream fis = null;
                String text;
                String contenutoFile = "";
                try {
                    fis = openFileInput(MainActivity.NOME_DEL_FILE);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);

                    while ((text = br.readLine()) != null)
                        contenutoFile = contenutoFile + text + "\n";

                    if(contenutoFile.equals("")){
                        record = true;
                        FileOutputStream fos = openFileOutput(MainActivity.NOME_DEL_FILE, MODE_PRIVATE);
                        String templateTabella = "";
                        for(int i = 0; i < 8; i++)
                            templateTabella = templateTabella + "--\n";
                        fos.write(templateTabella.getBytes());
                        fos.close();
                    }
                }catch (IOException ee){
                    ee.printStackTrace();
                }finally {
                    if(fis != null){
                        try {
                            fis.close();
                        }catch(IOException eee){
                            eee.printStackTrace();
                        }
                    }
                }

                System.out.println("Tabella di gioco: \n" + contenutoFile);
                if(!record)//Se nel file c'era già qualche record, si comparano con il nuovo valore
                    record = controlloRecord(contenutoFile.toString());

                if(stato == 2)
                    record = false;
                if(record){//Se è record viene chiamata la finestra di record
                    Intent i = new Intent(this, ActivityRecord.class);
                    startActivity(i);
                }else {//Se non è record viene mostrato l'immagine di un trofeo con la scritta "VITTORIA"
                    b.campoPerLascritta.setTextSize(40);
                    b.campoPerLascritta.setTextColor(Color.GREEN);
                    b.campoPerLascritta.setText(R.string.vittoria);
                    b.imageButton21.setVisibility(View.INVISIBLE);//Scambio dei pulsanti con "Indovina" con "Menù"
                    for (int i = 0; i < vettorePulsanti.length - 1; i++)
                        vettorePulsanti[i].setVisibility(View.INVISIBLE);//Rimozione dei tutti i tasti
                    b.imageView7.setVisibility(View.VISIBLE);
                }
            }
        }
    }//Chiusura del metodo di conferma numero

    private void ritornoAllaFinestraPrincipale(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private String presaDiTempo(){
        int rounded = (int) Math.round(tempoImpiegato);

        int secondi = ((rounded % 86400) % 3600) % 60;
        int minuti = ((rounded % 86400) % 3600) / 60;

        return String.format("%02d", minuti) + ":" + String.format("%02d", secondi);
    }

    private boolean controlloRecord(String contenutoFile){
        //Viene deciso quale parte della stringa viene estratta
        int valoreInTerzaPosizione = (6 * stato) - 1;
        StringBuilder tempoTerzaPosizione = new StringBuilder();

        for(int i = 0; i < contenutoFile.length(); i++){
            int contatoreDiTrattini = 0;

            //Quando viene trovata la corretta posizione, si scorre per prendere tutta la stringa
            if(contatoreDiTrattini == valoreInTerzaPosizione && contenutoFile.charAt(i) != '-')//Il valore '-' viene escluso
                tempoTerzaPosizione.append(contenutoFile.charAt(i));

            //Quando si trova il valore '-' va contato per trovare quello che in particolare serve a noi
            if(contenutoFile.charAt(i) == ('-'))
                contatoreDiTrattini++;
        }
        try {
            if (tempoImpiegato < Integer.parseInt(tempoTerzaPosizione.toString()))
                return true;
            else return false;
        }catch (NumberFormatException e){
            return true;
        }
    }
}