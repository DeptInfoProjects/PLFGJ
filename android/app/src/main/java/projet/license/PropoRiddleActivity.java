package projet.license;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

public class PropoRiddleActivity extends Activity {
    private EditText riddle;
    private EditText answer;
    private Button sendServer;
    private Controleur ctrl;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propo_riddle_activity);


        riddle = findViewById(R.id.riddle_propo);
        answer = findViewById(R.id.reponse_propo);
        sendServer = findViewById(R.id.myButtonSend);

        ctrl = new Controleur((Affichage) this);
        Connexion connexion = new Connexion("http://172.20.10.11:10101", ctrl);
        connexion.seConnecter();




        sendServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
