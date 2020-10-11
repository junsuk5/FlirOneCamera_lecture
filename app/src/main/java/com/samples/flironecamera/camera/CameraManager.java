package com.samples.flironecamera.camera;

import android.content.Context;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraManager {
    private Context context;
    private PreviewView finderView;
    private LifecycleOwner lifecycleOwner;
    private GraphicOverlay graphicOverlay;

    private Preview preview;
    private Camera camera;
    private ExecutorService cameraExecutor;
    private ProcessCameraProvider cameraProvider;

    private ImageAnalysis imageAnalysis;
    private ImageAnalysis.Analyzer faceAnalyzer;

    public CameraManager(Context context, PreviewView finderView, LifecycleOwner lifecycleOwner, GraphicOverlay graphicOverlay) {
        this.context = context;
        this.finderView = finderView;
        this.lifecycleOwner = lifecycleOwner;
        this.graphicOverlay = graphicOverlay;

        cameraExecutor = Executors.newSingleThreadExecutor();
        faceAnalyzer = new FaceDetectionProcessor(graphicOverlay);
    }

    public void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                preview = new Preview.Builder().build();

                imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, faceAnalyzer);

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                setCameraConfig(cameraProvider, cameraSelector);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(context));
    }

    private void setCameraConfig(ProcessCameraProvider cameraProvider, CameraSelector cameraSelector) {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            camera = cameraProvider.bindToLifecycle(lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis);
            preview.setSurfaceProvider(finderView.getSurfaceProvider());
        }
    }

}
