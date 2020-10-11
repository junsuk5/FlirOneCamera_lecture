package com.samples.flironecamera.camera;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

public class FaceDetectionProcessor implements ImageAnalysis.Analyzer {
    private GraphicOverlay graphicOverlay;

    private FaceDetectorOptions realTimeOpts = new FaceDetectorOptions.Builder()
            .build();

    private FaceDetector detector = FaceDetection.getClient(realTimeOpts);

    public FaceDetectionProcessor(GraphicOverlay graphicOverlay) {
        this.graphicOverlay = graphicOverlay;
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    @Override
    public void analyze(@NonNull ImageProxy image) {
        Image mediaImage = image.getImage();
        if (mediaImage != null) {
            detectImage(InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees()))
                    .addOnSuccessListener(results -> {
                        onSuccess(results, graphicOverlay, image.getCropRect());
                        image.close();
                    }).addOnFailureListener(e -> {
                        onFailure(e);
                        image.close();
                    });
        }

    }

    private Task<List<Face>> detectImage(InputImage image) {
        return detector.process(image);
    }

    public void stop() {
        detector.close();
    }

    private void onSuccess(List<Face> results, GraphicOverlay graphicOverlay, Rect rect) {
        graphicOverlay.clear();
        results.forEach(face -> {
            FaceGraphic2 faceGraphic = new FaceGraphic2(graphicOverlay, face, rect);
            graphicOverlay.add(faceGraphic);
        });
        graphicOverlay.postInvalidate();
    }

    private void onFailure(Exception e) {
        Log.w("FaceDetectionProcessor", "onFailure: ", e);
    }
}
