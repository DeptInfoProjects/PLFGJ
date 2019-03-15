package projet.license;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import commun.ListDemande;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Color.MAGENTA;


public class DrawDetectorActivity extends Activity implements View.OnClickListener, Affichage{

    private Button effacer, couleur, btnJouer, start, tutoriel;
    private TextView titre, fdem, randform, click, score,rightBord,leftBord;
    Controleur ctrl;
    public int i = 0;
    public static int scor = 0;
    private String formeDemande = "Press start";
    private ListDemande listDemande = new ListDemande();
    private File newImage;
    private PaintView myCanvas;
    final String[] formes = {"Point", "Segment", "Triangle", "Carre", "Rond"};
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopUp;
    private Context mContext;
    private int textCol;
    private int kyriakos = Color.WHITE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawdetector);
        this.init();


        ctrl = new Controleur(this);
        Connexion connexion = new Connexion("http://192.168.0.18:10101", ctrl);
        connexion.seConnecter();


    }
    public static Bitmap viewToBitmap(View view,int width,int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        return bitmap;
    }


    private void init() {
        effacer = findViewById(R.id.effacer);
        couleur = findViewById(R.id.color);
        btnJouer = findViewById(R.id.valider);
        start = findViewById(R.id.start);
        titre = findViewById(R.id.textView6);
        fdem = findViewById(R.id.textView4);
        randform = findViewById(R.id.textView3);
        tutoriel = findViewById(R.id.tutoriel);
        score = findViewById(R.id.score);
        click = (TextView) findViewById(R.id.click);
        rightBord = findViewById(R.id.rightBord);
        leftBord = findViewById(R.id.leftBord);




        mContext = getApplicationContext();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.container);
        myCanvas = (PaintView) findViewById(R.id.myCanvas);

        effacer.setOnClickListener(this);
        couleur.setOnClickListener(this);
        start.setOnClickListener(this);
        btnJouer.setOnClickListener(this);
        tutoriel.setOnClickListener(this);

    }

    public void back_home(View v){
        finish();    // retourner page home
    }

    private void changeTheme(int choix) {
        if(choix == Color.MAGENTA || choix ==Color.YELLOW || choix==Color.LTGRAY)
            { textCol = Color.WHITE;}
        else
            { textCol = Color.DKGRAY;}
        if(choix == Color.BLUE)
        { textCol = Color.WHITE;}
        //couleur.setBackgroundColor(choix);
        couleur.setTextColor(textCol);
        //start.setBackgroundColor(choix);
        start.setTextColor(textCol);
        //btnJouer.setBackgroundColor(choix);
        btnJouer.setTextColor(textCol);
        //effacer.setBackgroundColor(choix);
        effacer.setTextColor(textCol);
        titre.setBackgroundColor(choix);
        titre.setTextColor(textCol);
        fdem.setBackgroundColor(choix);
        fdem.setTextColor(textCol);
        randform.setBackgroundColor(choix);
        randform.setTextColor(textCol);
        //tutoriel.setBackgroundColor(choix);
        tutoriel.setTextColor(textCol);
        score.setBackgroundColor(choix);
        score.setTextColor(textCol);
        click.setBackgroundColor(choix);
        click.setTextColor(textCol);
        rightBord.setBackgroundColor(choix);
        rightBord.setTextColor(textCol);
        leftBord.setBackgroundColor(choix);
        leftBord.setTextColor(textCol);
        mRelativeLayout.setBackgroundColor(choix);





    }

    private int getColor() {
        switch (this.i) {
            case 0:
                this.i = this.i + 1;
                changeTheme(Color.BLACK);
                return Color.BLACK;
            case 1:
                this.i = this.i + 1;
                changeTheme(Color.LTGRAY);
                return Color.LTGRAY;
            case 2:
                this.i = this.i + 1;
                changeTheme(Color.MAGENTA);
                return MAGENTA;
            case 3:
                this.i = this.i + 1;
                changeTheme(Color.YELLOW);
                return Color.YELLOW;
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
                changeTheme(Color.LTGRAY);
                return Color.LTGRAY;
            case 1:
                changeTheme(Color.MAGENTA);
                return Color.MAGENTA;
            case 2:
                changeTheme(Color.YELLOW);
                return Color.YELLOW;
            case 3:
                changeTheme(Color.BLUE);
                return Color.BLUE;
            case 4:
                changeTheme(Color.BLACK);
                return Color.BLACK;
        }
        return Color.WHITE;
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
                myCanvas.clear();
                ctrl.msgReset();
                myCanvas.countTicks = 0;
                listDemande.setTicks(0);
                afficherTick();
                Log.d("Button Pressed : ",press.getText() + "");
                break;
            case R.id.start:
                leftBord.setBackgroundColor(mRelativeLayout.getSolidColor());
                rightBord.setBackgroundColor(mRelativeLayout.getSolidColor());
                //myCanvas.reset(getColorEffacer());
                newForme();
                ctrl.msgStart();
                Log.d("Button Pressed : ",press.getText() + "");
                myCanvas.countTicks = 0;
                afficherTick();
                break;
            case R.id.color:
                mRelativeLayout.setBackgroundColor(getColor());
                myCanvas.setBackgroundColor(Color.BLACK);
                myCanvas.mPaint.setColor(Color.WHITE);



                ctrl.msgColor();
                Log.d("Button Pressed : ", press.getText() + "");
                break;
            case R.id.valider:
                ctrl.msgValider(formeDemande +"," + myCanvas.getTicks());
                ctrl.sendImage(myCanvas.encodeBitMap());
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


    public void majScor(final boolean b){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (b){
                    String txt = ("Juste");
                    score.setText(txt);
                    leftBord.setBackgroundColor(Color.GREEN);
                }
                else {
                    String txt = ("Faux");
                    score.setText(txt);
                    rightBord.setBackgroundColor(Color.RED);


                }
            }
        });

    }



    public void startShare(){
        Bitmap bitmap = viewToBitmap(myCanvas,myCanvas.getWidth(),myCanvas.getHeight());
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(PNG,100,byteArrayOutputStream);
        newImage = new File(Environment.getExternalStorageDirectory()+File.separator+"Image.png");
        try {
            newImage.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(newImage);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        }
        catch (IOException e){
            e.printStackTrace();

        }

    }
 }


