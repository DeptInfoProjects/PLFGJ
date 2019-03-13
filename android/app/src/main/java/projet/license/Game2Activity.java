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

public class Game2Activity extends Activity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game2);


    }
    @Override
    public void onClick(View v) {

    }
}
