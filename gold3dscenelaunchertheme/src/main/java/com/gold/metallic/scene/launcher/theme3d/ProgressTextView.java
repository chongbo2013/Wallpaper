package com.gold.metallic.scene.launcher.theme3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by xff on 2018/9/21.
 */

public class ProgressTextView extends AppCompatTextView {
    public ProgressTextView(Context context) {
        super(context);
        init();
    }

    public ProgressTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    Drawable progressDrawable;
    public void init(){
        progressDrawable=getContext().getResources().getDrawable(R.drawable.progress_btn_bg_corner);
    }

    float progress=0.0f;
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(progressDrawable!=null) {
            int left = 0;
            int top = 0;
            int right = (int) (getWidth()*progress);
            int bottom = getHeight();
            canvas.save();
            canvas.clipRect(left,top,right,bottom);
            progressDrawable.setBounds(0,0,getWidth(),getHeight());
            progressDrawable.draw(canvas);
            canvas.restore();
        }
    }
}
