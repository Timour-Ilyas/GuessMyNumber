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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

        campo = (EditText) findViewById(R.id.editTextTextPersonName);

        salva = (ImageButton) findViewById(R.id.imageButton5);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomeGiocatore = campo.getText().toString().trim();

                if(!nomeGiocatore.isEmpty()){//Se è stato inserito un nome
                    //Salvataggio in memoria dei valori
                    FileOutputStream streamObject = null;
                    try {
                        streamObject = openFileOutput(MainActivity.NOME_DEL_FILE, MODE_PRIVATE);
                        streamObject.write(nomeGiocatore.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(streamObject != null){
                            try {
                                streamObject.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }//Chiusura del try catch interno
                        }//Chiusura if
                    }//Chiusura finally e dell'inserimento dei record nel file

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