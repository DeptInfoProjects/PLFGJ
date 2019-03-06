package projet.license;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;


public class PaintView extends View implements View.OnTouchListener {
    private final int defaultDot = 10;
    private final int defaultColor = Color.GRAY;
    private int mPenColor;
    private int mDotSize;
    private float mX,mY,mOldX,mOldY;
    public int countTicks = 0;
    private ArrayList<Path> mPaths;
    private ArrayList<Paint> mPaints;
    private Path mPath;
    private Paint mPaint;
    private float[] coord;
    private Canvas canvas;




    public PaintView(Context context) {
        super(context);
        this.init();
    }

    private void init() {
        this.mDotSize = defaultDot;
        this.mPenColor = defaultColor;
        this.mPaths = new ArrayList<Path>();
        this.mPaints = new ArrayList<Paint>();
        this.mPath = new Path();
        this.addPath(false);
        this.mX = this.mY = this.mOldY = this.mOldX =  (float) 0.0;
        this.countTicks = 0;
        this.setOnTouchListener(this);
        this.coord = new float[]{};

    }
    public int getTicks(){
        return this.countTicks;
    }

    public void addPath(boolean fill){
        mPath = new Path();
        mPaths.add(mPath);
        mPaint = new Paint();
        mPaints.add(mPaint);
        mPaint.setColor(mPenColor);
        if (!fill)
            mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mDotSize);
    }
    protected  void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for (int i = 0; i<mPaths.size();i++)
            canvas.drawPath(mPaths.get(i),mPaints.get(i));
        canvas.drawPath(mPath,mPaint);
    }



    public void setPenColor(int penColor){
        this.mPenColor = penColor;
        this.mPaint.setColor(mPenColor);
    }


    public PaintView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.init();
    }

    public PaintView(Context context,AttributeSet attrs,int defSystelAttr){
        super(context,attrs,defSystelAttr);
        this.init();
    }

    public void reset(int PenColor) {
        this.init();
        this.invalidate();
        setPenColor(PenColor);
        this.countTicks = 0;
    }
    public void addTick(){
        this.countTicks = this.countTicks + 1;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mX = event.getX();
        mY = event.getY();
        Log.d("Touched : "," ("+mX + "," + mY + ")");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.addPath(true);
                this.mPath.addCircle(mX,mY,mDotSize/2,Path.Direction.CW);
                this.addPath(false);
                this.mPath.moveTo(mX,mY);
                this.addTick();
                break;
            case MotionEvent.ACTION_MOVE:
                this.mPath.lineTo(mX,mY);

                break;
            case MotionEvent.ACTION_UP:
                this.addPath(true);
                if (mOldX == mX && mOldY == mY)
                    this.mPath.addCircle(mX,mY,mDotSize/2,Path.Direction.CW);

                break;
        }
        this.addElement(coord, mX);
        this.addElement(coord, mY);
        this.invalidate();
        mOldX = mX;
        mOldY = mY;
        return true;

    }

    public float[] getCoord(){
        return this.coord;
    }

    private float[] addElement(float[] a, float e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    public void drawline(Canvas canvas){
        for(int i = 0; i < coord.length - 4; i ++){

            canvas.drawLine(coord[i],coord[i+1],coord[i+2],coord[i+3],this.mPaint);
        }
    }
    public Canvas getCanvas(){
        return this.canvas;
    }

    public Paint getmPaint(){
        return this.mPaint;
    }


}
