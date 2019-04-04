package projet.license;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import commun.Affichage;
import commun.Connexion;
import commun.Controleur;

public class LoginActivity extends Activity implements View.OnClickListener,Affichage {
    private EditText username ;
    private TextView usernameShow;
    private Button valider;
    private Controleur ctrl;
    private String s;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameShow = findViewById(R.id.nameText);
        username = findViewById(R.id.loginName);
        valider = findViewById(R.id.loginConfim);
        valider.setOnClickListener(this);

        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http://192.168.43.175:10101", ctrl);
        connexion.seConnecter();

    }
    @Override
    public void onClick(View v) {
        s = username.getText().toString();
        usernameShow.setText(s);
        username.setVisibility(View.INVISIBLE);
        //ctrl.msgId(s);
        Intent intent = new Intent(this,HomeActivity.class);
        if (intent != null)
            startActivity(intent);
    }

    @Override
    public void majScor(boolean ok) {

    }

    @Override
    public void timeGameScor(Integer score, Integer tentative) {

    }

    @Override
    public void listTimeGame(String listFormeDem, String listFormeRec) {

    }

    @Override
    public void rtoGameScore(String coupJoueur, String coupServeur, String resultat) {

    }
}
