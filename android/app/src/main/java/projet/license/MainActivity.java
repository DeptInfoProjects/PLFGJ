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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import commun.ListDemande;


public class MainActivity extends Activity implements View.OnClickListener{

    private Button effacer, couleur, btnJouer, start, exit, tutoriel;
    private TextView msg, fdem, randform, click;
    Controleur ctrl;
    public int i = 0;
    private String formeDemande = "Press start";
    private ListDemande listDemande = new ListDemande();

    private PaintView myCanvas;
    final String[] formes = {"Point", "Segment", "Triangle", "Carre", "Rond"};
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopUp;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();


        ctrl = new Controleur();
        Connexion connexion = new Connexion("http://192.168.43.60:10101", ctrl);
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
        click = (TextView) findViewById(R.id.click);

        mContext = getApplicationContext();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container);
        myCanvas = (PaintView) findViewById(R.id.myCanvas);

        effacer.setOnClickListener(this);
        couleur.setOnClickListener(this);
        start.setOnClickListener(this);
        btnJouer.setOnClickListener(this);
        tutoriel.setOnClickListener(this);

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
        click.setBackgroundColor(choix);
        click.setTextColor(Color.WHITE);


    }

    private int getColor() {
        switch (this.i) {
            case 0:
                this.i = this.i + 1;
                changeTheme(Color.GREEN);
                return Color.GREEN;
            case 1:
                this.i = this.i + 1;
                changeTheme(Color.LTGRAY);
                return Color.LTGRAY;
            case 2:
                this.i = this.i + 1;
                changeTheme(Color.BLACK);
                return Color.BLACK;
            case 3:
                this.i = this.i + 1;
                changeTheme(Color.RED);
                return Color.RED;
            case 4:
                this.i = 0;
                changeTheme(Color.BLUE);
                return Color.BLUE;
        }
        return Color.BLUE;

    }

    private int getColorEffacer() {
        switch (this.i - 1) {
            case 0:
                changeTheme(Color.GREEN);
                return Color.GREEN;
            case 1:
                changeTheme(Color.LTGRAY);
                return Color.LTGRAY;
            case 2:
                changeTheme(Color.BLACK);
                return Color.BLACK;
            case 3:
                changeTheme(Color.RED);
                return Color.RED;
            case 4:
                changeTheme(Color.BLUE);
                return Color.BLUE;
        }
        return Color.BLUE;
    }

    private void afficherTick() {
        click.setText(myCanvas.countTicks+"");
    }

    public void newForme(){
        Random rand = new Random();
        int rando = rand.nextInt(5);
        String formeRandom = formes[rando];
        randform.setText(formeRandom);
        formeDemande = formeRandom;
        listDemande.setForme(formes[rando]);
        listDemande.setTicks(0);
    }
    public void onClick(View v){
        Button press = (Button)findViewById(v.getId());
        switch (v.getId()){
            case R.id.effacer:
                myCanvas.reset(getColorEffacer());
                ctrl.msgReset();
                myCanvas.countTicks = 0;
                listDemande.setTicks(0);
                afficherTick();
                Log.d("Button Pressed : ",press.getText() + "");
                break;
            case R.id.start:
                myCanvas.reset(getColorEffacer());
                newForme();
                ctrl.msgStart();
                Log.d("Button Pressed : ",press.getText() + "");
                myCanvas.countTicks = 0;
                afficherTick();
                break;
            case R.id.color:
                myCanvas.setPenColor(getColor());
                ctrl.msgColor();
                Log.d("Button Pressed : ", press.getText() + "");
                break;
            case R.id.valider:
                ctrl.msgValider(formeDemande +"," + myCanvas.getTicks());
                Log.d("Button Pressed : ",press.getText() + "");
                afficherTick();
                myCanvas.countTicks = 0;
                break;
            case R.id.tutoriel:
                ctrl.msgTutoriel();
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


    public void drawlines(){
        myCanvas.getCanvas().drawLines(myCanvas.getCoord(),myCanvas.getmPaint());
    }

 }


