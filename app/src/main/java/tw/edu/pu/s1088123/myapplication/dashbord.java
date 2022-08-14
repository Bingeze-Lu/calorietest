package tw.edu.pu.s1088123.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;

public class dashbord extends View{
    private int totleHeight, totleWeight;//總高度以及總寬度
    private float percentage;//進度條趴數
    private String originValue;//輸入值
    public dashbord(Context context) {
        super(context);
    }
    public dashbord(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public dashbord(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public dashbord(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private float dp2px(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
    public void setPercentage(float value) {
        if (value >= 0) {
            DecimalFormat df = new DecimalFormat("#####0.#");
            originValue = df.format(value);
            value = value * 180 / 100;
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(percentage, value);
            percentage = value;
            valueAnimator.addUpdateListener((vA -> {
                percentage = (float) vA.getAnimatedValue();
                postInvalidate();
            }));
            valueAnimator.setDuration(500);//設置動畫時間
            valueAnimator.start();
        }
    }
    public void setPercontage(float value,float max,float min){
        if (max > min) {
            DecimalFormat df = new DecimalFormat("#####0.#");
            float f1;
            if (value <= min) {
                f1 = (min - min) / (max - min);
                originValue = df.format(min);
            } else if (value >= max) {
                f1 = (max - min) / (max - min);
                originValue = df.format(max);
            } else {
                f1 = (value - min) / (max - min);
                originValue = df.format(value);
            }
            value = f1 * 180;

            ValueAnimator vA = ValueAnimator.ofFloat(percentage, value);
            percentage = value;
            vA.addUpdateListener((v -> {
                percentage = (float) vA.getAnimatedValue();
                postInvalidate();
            }));
            vA.setDuration(500);
            vA.start();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);//消除鋸齒
        int bottomColor = Color.parseColor("#C9C9C9");//設置顏色
        paint.setColor(bottomColor);//設置底部顏色
        paint.setStrokeJoin(Paint.Join.ROUND);//設置畫筆畫出的形狀
        paint.setStrokeCap(Paint.Cap.ROUND);//使線的尾端具有圓角
        paint.setStyle(Paint.Style.STROKE);//使圓為空心
        paint.setStrokeWidth(dp2px(40));//設置外圍線的粗度
        RectF mRecF = new RectF(10 + dp2px(20), 10 + dp2px(20)
                , getWidth() - 10 - dp2px(20), getHeight() - 10);//畫一個扇形
        canvas.drawArc(mRecF, 180, 180, false, paint);//將扇形畫至畫布上
        paint.setColor(Color.parseColor("#1C98EB"));//把顏色換成藍色
        canvas.drawArc(mRecF, 180, percentage, false, paint);//畫進度
        Paint point = new Paint();
        point.setAntiAlias(true);
        point.setColor(Color.parseColor("#096148"));
        double x, y, Rx1, Ry1, Rx2, Ry2;
        float ovalbottom = getHeight() + 4 * 16;//指針下移點數(可以改為*100看看效果，你就會懂了)
        int xPos = (getWidth() / 2);//畫布X軸中心點
        int yPos = (getHeight() / 2);//畫布Y軸中心點
        float px = 50;//指針寬度
        x = xPos + Math.cos(
                Math.toRadians((percentage - 180))) * (yPos * 2 / 3);//針頭x座標,指針長度=(yPos * 2 / 3)
        y = ovalbottom - yPos + Math.sin(
                Math.toRadians((percentage - 180))) * (yPos * 2 / 3);//針頭y座標,指針長度=(yPos * 2 / 3)
        Rx1 = xPos + Math.cos(Math.toRadians(((percentage - 180) + 90))) * px;//指針底部第一點X座標
        Ry1 = ovalbottom - yPos + Math.sin(Math.toRadians(((percentage - 180) + 90))) * px;//指針底部第一點Y座標
        Rx2 = xPos + Math.cos(Math.toRadians(((percentage - 180) - 90))) * px;//指針底部第二點X座標
        Ry2 = ovalbottom - yPos + Math.sin(Math.toRadians(((percentage - 180) - 90))) * px;//指針底部第二點X座標
        canvas.drawCircle(getWidth() / 2, getHeight() / 2 + 64+0.8f, px - 0.5f, point);//畫一個圓遮醜
        Path path = new Path();//畫一個三角形
        path.moveTo((int) x, (int) y);
        path.lineTo((int) Rx1, (int) Ry1);
        path.lineTo((int) Rx2, (int) Ry2);
        path.close();
        canvas.drawPath(path, point);//畫進去畫布內
        paint.reset();//重置畫筆
        paint.setColor(Color.BLACK);//設置為黑色
        paint.reset();//重置畫筆
        paint.setColor(Color.BLACK);//設置為黑色
        paint.setTextSize(80);//設置字體大小
        if (originValue == null){//若還尚未輸入，則顯示0
            originValue = "0";
        }
        canvas.drawText("值:"+originValue,getHeight()/2-50,getWidth()/2+200,paint);//畫上去

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totleHeight = MeasureSpec.getSize(heightMeasureSpec);
        totleWeight = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(totleWeight, totleHeight);
    }

}
