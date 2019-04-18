package projet.license;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

public class PropositionActivity extends Activity implements View.OnClickListener , Affichage {

    private Spinner spinnerForme;
    private EditText myquestion;
    private EditText myreponse;
    private Button mysubmit;
    private Controleur ctrl;
    private String senigme;
    private String sreponse;
    private String[] list = {"Circle","Carre","Triangle"};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propo_activity);


        myquestion = findViewById(R.id.question);
        spinnerForme = findViewById(R.id.reponse);
        mysubmit = findViewById(R.id.submit);







        mysubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               senigme = myquestion.getText().toString();
               sreponse = spinnerForme.getSelectedItem().toString();
               String allpropo = senigme + ";" + sreponse ;
               if (senigme.length() > 0){
               ctrl.enigmeValider(allpropo);}



            }
        });


        ctrl = new Controleur((Affichage) this);
        Connexion connexion = new Connexion("http://172.20.10.11:10101", ctrl);
        connexion.seConnecter();






    }





    @Override
    public void onClick(View v) {

    }

    @Override
    public void majGraphic(String message, Bundle parameters) {

    }
}
