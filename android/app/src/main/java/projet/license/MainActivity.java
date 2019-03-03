package projet.license;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import java.util.Random;




public class MainActivity extends Activity implements View.OnClickListener {

    private Button effacer , couleur,btnJouer,start,exit,tutoriel ;
    private TextView msg,fdem,randform;
    TextView titre;
    Controleur ctrl;
    public int i = 0;
    private PaintView myCanvas;
    final String[] formes = {"Point","Segment","Triangle","Carre","Rond"} ;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopUp;
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();


        ctrl = new Controleur();
        pressValider();
        //pressCouleur();
        //pressEffacer();
        //pressStart();

        Connexion connexion = new Connexion("http://192.168.0.18:10101", ctrl);
        connexion.seConnecter();


    }

    private void init() {
        effacer = findViewById(R.id.effacer);
        couleur = findViewById(R.id.color);
        btnJouer = findViewById(R.id.valider);
        start = findViewById(R.id.start);
        msg = findViewById(R.id.textView6);
        fdem = findViewById(R.id.textView4);
        randform = findViewById(R.id.textView3);
        tutoriel = findViewById(R.id.tutoriel);
        exit = findViewById(R.id.exit);

        mContext = getApplicationContext();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container);
        myCanvas = (PaintView) findViewById(R.id.myCanvas);

        effacer.setOnClickListener(this);
        couleur.setOnClickListener(this);
        start.setOnClickListener(this);
        btnJouer.setOnClickListener(this);
        tutoriel.setOnClickListener(this);

    }

    public void afficheMessage(final String texte) {

        // appelÃ© depuis le thread de socketIO
        msg.post(new Runnable() {
            @Override
            public void run() {
                msg.setText(texte);
            }
        });

    }

    private void changeTheme(int choix) {
        couleur.setBackgroundColor(choix);
        couleur.setTextColor(Color.WHITE);
        start.setBackgroundColor(choix);
        start.setTextColor(Color.WHITE);
        btnJouer.setBackgroundColor(choix);
        btnJouer.setTextColor(Color.WHITE);
        effacer.setBackgroundColor(choix);
        effacer.setTextColor(Color.WHITE);
        msg.setBackgroundColor(choix);
        msg.setTextColor(Color.WHITE);
        fdem.setBackgroundColor(choix);
        fdem.setTextColor(Color.WHITE);
        randform.setBackgroundColor(choix);
        randform.setTextColor(Color.WHITE);
        tutoriel.setBackgroundColor(choix);
        tutoriel.setTextColor(Color.WHITE);
        exit.setBackgroundColor(choix);
        exit.setTextColor(Color.WHITE);


    }

    private void pressValider() {
        btnJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ctrl.apresDessin();
                ctrl.pressValider();
            }
        });
    }
    private void pressStart() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.pressStart();
            }
        });
    }
    private void pressEffacer() {
        effacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.pressReset();
            }
        });
    }

    private void pressCouleur(){
       couleur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ctrl.pressColor();
            }
        });
    }

    private int getColor(){
        switch (this.i) {
            case 0:
                this.i = this.i+1;
                changeTheme(Color.GREEN);
                return Color.GREEN;
            case 1:
                this.i = this.i+1;
                changeTheme(Color.LTGRAY);
                return Color.LTGRAY;
            case 2:
                this.i = this.i+1;
                changeTheme(Color.BLACK);
                return Color.BLACK;
            case 3:
                this.i = this.i +1;
                changeTheme(Color.RED);
                return Color.RED;
            case 4:
                this.i = 0;
                changeTheme(Color.BLUE);
                return Color.BLUE;
        }
        return Color.BLUE;

    }

    public void onClick(View v){
        Button press = (Button)findViewById(v.getId());

        switch (v.getId()){
            case R.id.effacer:
                myCanvas.reset();
                Log.d("Button Pressed : ",press.getText() + "");
                break;
            case R.id.start:
                Random rand = new Random();
                int rando = rand.nextInt(5);
                randform.setText(formes[rando]);
                Log.d("Button Pressed : ",press.getText() + "");
                break;
            case R.id.color:
                myCanvas.setPenColor(getColor());
                Log.d("Button Pressed : ", press.getText() + "");
                break;
            case R.id.valider:
                Log.d("Button Pressed : ",press.getText() + "");
                break;
            case R.id.tutoriel:
                LayoutInflater inflater =(LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.custom_layout,null);

                mPopUp = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );
                if(Build.VERSION.SDK_INT>=21){
                    mPopUp.setElevation(5.0f);
                }
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopUp.dismiss();
                    }
                });
                mPopUp.showAtLocation(findViewById(R.id.container), Gravity.CENTER,0,0);
                break;
        }
    }
}

