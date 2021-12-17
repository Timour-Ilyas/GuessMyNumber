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

        campo = (EditText) findViewById(R.id.editTextTextPersonName);

        salva = (ImageButton) findViewById(R.id.imageButton5);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeGiocatore = campo.getText().toString().trim();

                if(!nomeGiocatore.isEmpty()){//Se è stato inserito un nome salva in memoria dei valori
                    //Lettura dal file per avere lo stato attuale
                    FileInputStream fis = null;
                    String text;
                    StringBuilder contenutoFile = new StringBuilder();
                    try {
                        fis = openFileInput(MainActivity.NOME_DEL_FILE);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        StringBuilder sb = new StringBuilder();

                        while ((text = br.readLine()) != null){
                            contenutoFile.append("\n").append(text);
                            sb.append(text).append("\n");
                        }

                        if(contenutoFile.toString().equals("")){
                            FileOutputStream fos = null;

                            fos = openFileOutput(MainActivity.NOME_DEL_FILE, MODE_PRIVATE);
                            fos.write(("--\n" +
                                    "--\n" +
                                    "--\n" +
                                    "--\n" +
                                    "--\n" +
                                    "--\n" +
                                    "--\n" +
                                    "--\n").getBytes());
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

                    openActivityScore();
                }else Toast.makeText(ActivityRecord.this, "Inserisci un nome", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openActivityScore(){
        Intent i = new Intent( this, MainActivity.class);
        startActivity(i);
    }
}