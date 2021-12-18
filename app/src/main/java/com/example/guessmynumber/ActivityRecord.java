package com.example.guessmynumber;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.guessmynumber.databinding.ActivityRecordBinding;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityRecord extends AppCompatActivity {
    private ImageButton salva;
    private EditText campo;

    private String nomeGiocatore = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record);

        ActivityRecordBinding b;
        b = ActivityRecordBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        String testo = ActivityDiGioco.contatore + " tentativi, ";
        if(ActivityDiGioco.stato == 1)
            testo += "facile";
        else if (ActivityDiGioco.stato == 2)
            testo += "Normale";
        else testo += "Difficile";

        b.textView3.setText(testo);

        campo = findViewById(R.id.editTextTextPersonName);

        salva = findViewById(R.id.imageButton5);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeGiocatore = campo.getText().toString().trim();

                if(!nomeGiocatore.isEmpty() && !nomeGiocatore.contains("-")){//Se è stato inserito un nome salva in memoria dei valori e non contiene trattini
                    //Lettura dal file per avere lo stato attuale
                    FileInputStream fis = null;
                    String text;
                    String contenutoFile = "";

                    int contaLinee = 1;
                    if(ActivityDiGioco.stato == 2)
                        contaLinee = 4;
                    else if(ActivityDiGioco.stato == 3)
                        contaLinee = 7;

                    try {
                        fis = openFileInput(MainActivity.NOME_DEL_FILE);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);

                        int lineatore = 1;
                        while ((text = br.readLine()) != null) {

                            //È stata inizializzata la riga di partenza
                            if(lineatore == contaLinee || lineatore == contaLinee-1 || lineatore == contaLinee-2) {
                                /*
                                 * Se la linea scelta è quella corretta oppure se è quella successiva o quella successiva ancora
                                 * salva la linea nella stringa
                                 */
                                contenutoFile = contenutoFile + text + "\n";
                            }
                            lineatore++;
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

                    //Dopo aver compreso lo stato attuale del podio, capire in che posizione inserire il nuovo record
                    try {
                        fis = openFileInput(MainActivity.NOME_DEL_FILE);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        FileOutputStream fos = openFileOutput(MainActivity.NOME_DEL_FILE, MODE_PRIVATE);

                        /*
                         * Comparazione valore estrapolato con il valore contatore dell'ultima partita
                         * Non si testa la terza posizione perché una prova già stata svolta dalla classe ActivityDiGioco
                         *
                         * Il primo parametro identifica la posizione di riferimento
                         * Il secondo parametro identifica:
                         *      1: Nome
                         *      2: Numero tentativi
                         *      3: Tempo
                         */
                        System.out.println("Tabella1:\n" + contenutoFile);
                        if(String.valueOf(estraiValore(2,2))== "" || ActivityDiGioco.contatore < Integer.parseInt(estraiValore(2,2))){
                            if(String.valueOf(estraiValore(1,2))== "" || ActivityDiGioco.contatore < Integer.parseInt(estraiValore(1,2))){
                                //Prima posizione
                                modificaTabella(contaLinee,1,nomeGiocatore);
                                modificaTabella(contaLinee,2,String.valueOf(ActivityDiGioco.contatore));
                                modificaTabella(contaLinee,3,String.valueOf(ActivityDiGioco.tempoImpiegato));
                            }else{
                                //Seconda posizione
                                modificaTabella(contaLinee+1,1,nomeGiocatore);
                                modificaTabella(contaLinee+1,2,String.valueOf(ActivityDiGioco.contatore));
                                modificaTabella(contaLinee+1,3,String.valueOf(ActivityDiGioco.tempoImpiegato));
                            }
                        }else{
                            //Terza posizione
                            modificaTabella(contaLinee+2,1,nomeGiocatore);
                            modificaTabella(contaLinee+2,2,String.valueOf(ActivityDiGioco.contatore));
                            modificaTabella(contaLinee+2,3,String.valueOf(ActivityDiGioco.tempoImpiegato));
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        if(fis != null){
                            try {
                                fis.close();
                            }catch(IOException eee){
                                eee.printStackTrace();
                            }
                        }
                    }

                    System.out.println("Tabella2:\n" + contenutoFile);
                    openActivityScore();
                }else Toast.makeText(ActivityRecord.this, "Inserisci un nome", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openActivityScore(){
        Intent i = new Intent( this, MainActivity.class);
        startActivity(i);
    }

    private String estraiValore(int contaLinee, int sceltaDiStringa){
        int lineatore = 1;
        String valore = "";
        FileInputStream fis = null;
        String text;
        String contenutoFile = "";
        try {
            fis = openFileInput(MainActivity.NOME_DEL_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((text = br.readLine()) != null) {
                if (lineatore == contaLinee) {
                    //Identificata la linea
                    for (int i = 0; i < text.length(); i++) {
                        //Identificazione del primo pezzo
                        if(sceltaDiStringa == 1){
                            if (text.charAt(i) != '-')
                                //Estrapolazione del valore
                                valore += text.charAt(i);
                            else
                                //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                i = text.length();
                        }

                        if(sceltaDiStringa == 2) {//Se si vuole il numero di tentativi
                            //Identificazione del secondo pezzo
                            if (text.charAt(i) == '-') {
                                for (int j = i+1; j < text.length(); j++) {
                                    if (text.charAt(j) != '-')
                                        //Estrapolazione del valore
                                        valore += text.charAt(j);
                                    else {
                                        //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                        j = text.length();
                                        i = j;
                                    }
                                }
                            }
                        }

                        if(sceltaDiStringa == 3){
                            //Identificazione del terzo pezzo
                            if (text.charAt(i) == '-') {
                                for (int j = i+1; j < text.length(); j++) {
                                    if (text.charAt(j) == '-') {
                                        for (int k = j+1; k < text.length(); k++) {
                                            if(text.charAt(k) != '-'){
                                                //Estrapolazione del valore
                                                valore += text.charAt(k);
                                            }else{
                                                //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                                k = text.length();
                                                j = k;
                                                i = j;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                lineatore++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                }catch(IOException eee){
                    eee.printStackTrace();
                }
            }
        }
        return valore;
    }

    private void modificaTabella(int contaLinee, int sceltaDiStringa, String valoreModificante){
        //Modifica del valore
        int lineatore = 1;

        String valore = "";
        FileInputStream fis = null;
        String text;
        String contenutoFile = "";
        try {
            fis = openFileInput(MainActivity.NOME_DEL_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((text = br.readLine()) != null) {
                if (lineatore == contaLinee) {
                    //Identificata la linea
                    for (int i = 0; i < text.length(); i++) {
                        //Identificazione del primo pezzo
                        if(sceltaDiStringa == 1){
                            //In modo da inserire il valore una sola volta
                            boolean monoVolta = true;
                            if(monoVolta) {
                                contenutoFile += valoreModificante;
                                monoVolta = false;
                            }

                            //Aggiunta secondo pezzo
                            if (text.charAt(i) == '-') {
                                for (int j = i+1; j < text.length(); j++) {
                                    if (text.charAt(j) != '-')
                                        //Modifica del valore
                                        contenutoFile += text.charAt(j);
                                    else {
                                        //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                        contenutoFile += "-";
                                        j = text.length();
                                        i = j;
                                    }
                                }
                            }

                            //Aggiunta terzo pezzo
                            if (text.charAt(i) == '-') {
                                for (int j = i+1; j < text.length(); j++) {
                                    if (text.charAt(j) == '-') {
                                        contenutoFile += "-";
                                        for (int k = j+1; k < text.length(); k++) {
                                            if(text.charAt(k) != '-'){
                                                //Modifica del valore
                                                contenutoFile += text.charAt(k);
                                            }else{
                                                //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                                contenutoFile += "-";
                                                k = text.length();
                                                j = k;
                                                i = j;
                                            }
                                        }
                                    }
                                }
                            }
                        }//Fine del primo caso

                        if(sceltaDiStringa == 2) {//Se si vuole il numero di tentativi
                            //Aggiunta primo pezzo
                            if (text.charAt(i) != '-')
                                //Estrapolazione del valore
                                valore += text.charAt(i);
                            else {
                                //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                valore += "-";
                                i = text.length();
                            }

                            //In modo da inserire il valore una sola volta
                            boolean monoVolta = true;
                            if(monoVolta) {
                                contenutoFile += valoreModificante;
                                monoVolta = false;
                            }

                            //Aggiunta terzo pezzo
                            if (text.charAt(i) == '-') {
                                for (int j = i+1; j < text.length(); j++) {
                                    if (text.charAt(j) == '-') {
                                        contenutoFile += "-";
                                        for (int k = j+1; k < text.length(); k++) {
                                            if(text.charAt(k) != '-'){
                                                //Modifica del valore
                                                contenutoFile += text.charAt(k);
                                            }else{
                                                //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                                contenutoFile += "-";
                                                k = text.length();
                                                j = k;
                                                i = j;
                                            }
                                        }
                                    }
                                }
                            }
                        }//Fine del secondo caso

                        if(sceltaDiStringa == 3){
                            //Aggiunta primo pezzo
                            if (text.charAt(i) != '-')
                                //Estrapolazione del valore
                                valore += text.charAt(i);
                            else {
                                //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                valore += "-";
                                i = text.length();
                            }

                            //Aggiunta secondo pezzo
                            if (text.charAt(i) == '-') {
                                for (int j = i+1; j < text.length(); j++) {
                                    if (text.charAt(j) != '-')
                                        //Modifica del valore
                                        contenutoFile += text.charAt(j);
                                    else {
                                        //Chiusura dei cicli nel caso si sia riusciti ad estrapolare il valore
                                        contenutoFile += "-";
                                        j = text.length();
                                        i = j;
                                    }
                                }
                            }

                            //In modo da inserire il valore una sola volta
                            boolean monoVolta = true;
                            if(monoVolta) {
                                contenutoFile += valoreModificante;
                                monoVolta = false;
                            }
                        }//Fine del terzo caso
                    }
                }else
                    contenutoFile = contenutoFile + text + "\n";

                lineatore++;
            }//Fine della ricerca nel file
            //Aggiornamento del file
            FileOutputStream fos = openFileOutput(MainActivity.NOME_DEL_FILE, MODE_PRIVATE);
            fos.write(contenutoFile.getBytes());
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                }catch(IOException eee){
                    eee.printStackTrace();
                }
            }
        }
    }

    private String stampaTabella(){
        FileInputStream fis = null;
        String text;
        String contenutoFile = "";
        try {
            fis = openFileInput(MainActivity.NOME_DEL_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((text = br.readLine()) != null)
                contenutoFile = contenutoFile + text + "\n";

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
        return contenutoFile;
    }
}