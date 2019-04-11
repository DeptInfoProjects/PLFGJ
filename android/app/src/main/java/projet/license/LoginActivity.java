package projet.license;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gestion.Affichage;
import gestion.Connexion;
import gestion.Controleur;

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
        Connexion connexion = new Connexion("http://172.20.10.11:10101", ctrl);
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
    public void majGraphic(String message, Bundle parameters) {

    }
}
