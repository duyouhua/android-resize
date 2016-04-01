package cn.mindstack.example;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.mindstack.androidresize.HighlightFrame;
import cn.mindstack.androidresize.HighlightView;

public class MainActivity extends AppCompatActivity {

    private HighlightFrame highlightFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highlightFrame = (HighlightFrame) findViewById(R.id.highlightframe);

        highlightFrame.post(new Runnable() {
            @Override
            public void run() {
                HighlightView hv = new HighlightView(highlightFrame);
                final int width = highlightFrame.getWidth();
                final int height = highlightFrame.getHeight();

                Rect imageRect = new Rect(0, 0, width, height);

                // Make the default size about 4/5 of the width or height
                int cropWidth = Math.min(width, height) * 4 / 5;
                @SuppressWarnings("SuspiciousNameCombination")
                int cropHeight = cropWidth;

//                if (aspectX != 0 && aspectY != 0) {
//                    if (aspectX > aspectY) {
//                        cropHeight = cropWidth * aspectY / aspectX;
//                    } else {
//                        cropWidth = cropHeight * aspectX / aspectY;
//                    }
//                }

                int x = (width - cropWidth) / 2;
                int y = (height - cropHeight) / 2;

                RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
                hv.setup(new Matrix(), imageRect, cropRect, true);//aspectX != 0 && aspectY != 0);
                hv.setFocus(true);
                highlightFrame.add(hv);
            }
        });
    }
}
