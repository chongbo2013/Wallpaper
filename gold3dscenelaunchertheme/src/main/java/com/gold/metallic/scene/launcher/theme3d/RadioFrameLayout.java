package com.gold.metallic.scene.launcher.theme3d;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by x002 on 2018/9/21.
 */

public class RadioFrameLayout extends FrameLayout {
    float radio=2160f/1080f;
    public RadioFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RadioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height= (int) (widthSize*radio);
        int heightMeasureSpecNew= MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpecNew);
    }


}
