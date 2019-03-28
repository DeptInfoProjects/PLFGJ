package projet.license;

import androidx.appcompat.app.AppCompatActivity;
import commun.Identification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener,Affichage {
    private EditText username;
    private Button login;
    private Controleur ctrl;
    private Identification ident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        login = findViewById(R.id.btn_play);
        username = findViewById(R.id.editText);
        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http:/172.20.10.2:10101",ctrl);
        connexion.seConnecter();


        login.setOnClickListener(this);


    }




    @Override
    public void onClick(View v) {
        Intent intent = null;
        Integer  vID = v.getId();
        if (vID == R.id.btn_play){
        intent = new Intent(this,MainHome.class);}
        if (intent != null) {
            ctrl.setIdentification(username.getText().toString());
            ctrl.aprèsConnexion();
            startActivity(intent);
        }

    }

    @Override
    public void majScor(boolean ok) {

    }

    @Override
    public void timeGameScor(Integer score, Integer tentative) {

    }

    @Override
    public void listTimeGame(List<String> listFormeDem, List<String> listFormeRec) {

    }

    @Override
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

    }
}
