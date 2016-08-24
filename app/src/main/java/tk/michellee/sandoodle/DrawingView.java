package tk.michellee.sandoodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by michelle on 8/14/16.
 * A simple drawing app project that users can choose from a variety of "sand-textured" color.
 * Users can save, create new file, adjust brush size, and erase.
 * More features will be added in the future, including a menu screen and settings
 */
public class DrawingView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFF660000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap, sandColor;
    private boolean erase=false;
    private float brushSize,lastBrushSize;
    private Shader shader;

    public DrawingView(Context context, AttributeSet attributeset){
        super(context, attributeset);
        setupDrawing();
    }

    private void setupDrawing(){
        //set up drawing area
        //set up Path to trace users' drawing action
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.BUTT);

        canvasPaint = new Paint(Paint.DITHER_FLAG);

        brushSize = getResources().getInteger(R.integer.size2);
        lastBrushSize = brushSize;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        //view given size
        super.onSizeChanged(w, h, oldw, oldh);

        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas){
        //draw view
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setColor(String newColor) {
        //set color
        invalidate();

        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);

        if (paintColor == 0xffff0000) {
            sandColor = BitmapFactory.decodeResource(
                    getResources(), R.drawable.pink);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff0000ff) {
            sandColor = BitmapFactory.decodeResource(
                    getResources(), R.drawable.blue);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff660000) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.offwhite);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xffff6600) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.orange);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xffffcc00) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.sand);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff009900) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.mint);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff009999) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.teal);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff990099) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.purple);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xffff6666) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.green);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xffffffff) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.white);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff787878) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.grey);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        } else if (paintColor == 0xff000000) {
            sandColor = BitmapFactory.decodeResource(getResources(), R.drawable.black);
            shader = new BitmapShader(sandColor,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }

        drawPaint.setShader(shader);
    }


    public void setErase(boolean isErase){
        //set erase true or false;
        erase = isErase;
        if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }

    public void setBrushSize(float newSize){
        //update size
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }

    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void startNew(){
        drawCanvas.drawColor(0,PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
