package com.ricahrd.customrecdb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jiangwei on 16/6/6.
 */
public class View extends ImageView{

    private float scaleWidth, scaleHeight;
    private int newWidth, newHeight;
    private Matrix mMatrix = new Matrix();
    private Bitmap indicatorBitmap;
    private Paint paint = new Paint();
    static final long  ANIMATION_INTERVAL = 100;


    public View(Context context) {
        super(context);
    }

    public View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.noise_index);
        int bitmapWidth = myBitmap.getWidth();
        int bitmapHeight = myBitmap.getHeight();
        newWidth = getWidth();
        newHeight = getHeight();
        scaleWidth = ((float) newWidth) /(float) bitmapWidth;  // 获取缩放比例
        scaleHeight = ((float) newHeight) /(float) bitmapHeight;  //获取缩放比例
        mMatrix.postScale(scaleWidth, scaleHeight);   //设置mMatrix的缩放比例
        indicatorBitmap = Bitmap.createBitmap(myBitmap, 0, 0, bitmapWidth, bitmapHeight, mMatrix,true);  //获取同等和背景宽高的指针图的bitmap

        paint = new Paint();
        paint.setTextSize(55);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);  //抗锯齿
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (indicatorBitmap == null) {
            init();
        }
        float currentAngle = getAngle(Calculator.dbstart);
        mMatrix.setRotate(currentAngle, newWidth / 2, newHeight * 215 / 460);   //片相对位置
        canvas.drawBitmap(indicatorBitmap, mMatrix, paint);
        postInvalidateDelayed(ANIMATION_INTERVAL);
        canvas.drawText((int)Calculator.dbstart +" DB", newWidth/2,newHeight*36/46, paint); //图片相对位置
    }

    private float getAngle(float db){
        return(db-85)*5/3;  //说多了都是泪，网上找的图片。。自己不会改图，代码计算下
    }
}
