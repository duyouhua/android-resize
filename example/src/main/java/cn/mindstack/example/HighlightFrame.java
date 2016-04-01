/* 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 tony
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cn.mindstack.example;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.mindstack.androidresize.HighlightView;

/**
 * Created by tony on 16/3/31.
 */
public class HighlightFrame extends RelativeLayout {

    private ArrayList<HighlightView> highlightViews = new ArrayList<HighlightView>();
    private HighlightView motionHighlightView;

    private float lastX;
    private float lastY;
    private int motionEdge;
    private int validPointerId;

    public HighlightFrame(Context context) {
        super(context);
    }

    public HighlightFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HighlightFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (HighlightView hv : highlightViews) {
            hv.invalidate();
            if (hv.hasFocus()) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (HighlightView hv : highlightViews) {
                    int edge = hv.getHit(event.getX(), event.getY());
                    if (edge != HighlightView.GROW_NONE) {
                        motionEdge = edge;
                        motionHighlightView = hv;
                        lastX = event.getX();
                        lastY = event.getY();
                        // Prevent multiple touches from interfering with crop area re-sizing
                        validPointerId = event.getPointerId(event.getActionIndex());
                        motionHighlightView.setMode((edge == HighlightView.MOVE)
                                ? HighlightView.ModifyMode.Move
                                : HighlightView.ModifyMode.Grow);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (motionHighlightView != null) {
                    motionHighlightView.setMode(HighlightView.ModifyMode.None);
                }
                motionHighlightView = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (motionHighlightView != null && event.getPointerId(event.getActionIndex()) == validPointerId) {
                    motionHighlightView.handleMotion(motionEdge, event.getX()
                            - lastX, event.getY() - lastY);
                    lastX = event.getX();
                    lastY = event.getY();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (HighlightView highlightView : highlightViews) {
            highlightView.draw(canvas);
        }
    }

    public void add(HighlightView hv) {
        highlightViews.add(hv);
        invalidate();
    }
}
