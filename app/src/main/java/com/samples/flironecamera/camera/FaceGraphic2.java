package com.samples.flironecamera.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.google.mlkit.vision.face.Face;

public class FaceGraphic2 extends GraphicOverlay.Graphic {
    private static final float ID_TEXT_SIZE = 80.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private Face face;
    private Rect imageRect;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    public FaceGraphic2(GraphicOverlay overlay, Face face, Rect imageRect) {
        super(overlay);
        this.face = face;
        this.imageRect = imageRect;

        final int selectedColor = Color.RED;

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect = calculateRect(imageRect.height(), imageRect.width(), face.getBoundingBox());
        canvas.drawRect(rect, mBoxPaint);

        float x = (rect.right + rect.left) / 2.0f;
        float y = (rect.top + rect.bottom) / 2.0f;

        canvas.drawText("id: " + face.getTrackingId(), x, y, mIdPaint);
    }
}
