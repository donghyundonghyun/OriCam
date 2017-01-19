package com.example.donghyun.myhack;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.util.Log;
import android.util.Pair;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;

import java.io.IOException;

/**
 * Created by Ahn on 2017-01-19.
 */




public class PastPreview {

    CameraActivity CA;

    public static String TAG = "CameraActivity";

    private CameraSurfaceTextureListener mCameraSurfaceTextureListener;
    private OrientationEventListener mOrientationEventListener;
    private TextureView mTextureView;


    protected  PastPreview(CameraActivity ma, TextureView tv) {
        CA=ma;


        mCameraSurfaceTextureListener = new CameraSurfaceTextureListener(ma);

        mTextureView = tv;
        mTextureView.setSurfaceTextureListener(mCameraSurfaceTextureListener);
    }



    protected void onResume() {

        if (mOrientationEventListener == null) {
            mOrientationEventListener = new OrientationEventListener(CA, SensorManager.SENSOR_DELAY_NORMAL) {
                private int mOrientation;

                @Override
                public void onOrientationChanged(int orientation) {
                    int lastOrientation = mOrientation;

                    if (orientation >= 315 || orientation < 45) {
                        if (mOrientation != Surface.ROTATION_0) {
                            mOrientation = Surface.ROTATION_0;
                        }
                    } else if (orientation >= 45 && orientation < 135) {
                        if (mOrientation != Surface.ROTATION_90) {
                            mOrientation = Surface.ROTATION_90;
                        }
                    } else if (orientation >= 135 && orientation < 225) {
                        if (mOrientation != Surface.ROTATION_180) {
                            mOrientation = Surface.ROTATION_180;
                        }
                    } else if (mOrientation != Surface.ROTATION_270) {
                        mOrientation = Surface.ROTATION_270;
                    }

                    if (lastOrientation != mOrientation) {
                        Log.d("!!!!", "rotation!!! lastOrientation:"
                                + lastOrientation + " mOrientation:"
                                + mOrientation + " orientaion:"
                                + orientation);
                    }
                }
            };
        }

        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
    }


    protected void onPause() {
        mOrientationEventListener.disable();
    }


}


interface CameraHelper {
    Camera getCamera();
}

class CameraSurfaceTextureListener implements
        TextureView.SurfaceTextureListener {
    private Camera mCamera;
    private Activity mActivity;
    private Camera.CameraInfo mBackCameraInfo;

    public CameraSurfaceTextureListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface,
                                            int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        return true;
    }

    @Override
    public void onSurfaceTextureAvailable(
            SurfaceTexture surface,
            int width, int height) {
        Log.d("!!!!", "onSurfaceTextureAvailable!!!");
        Pair<Camera.CameraInfo, Integer> backCamera = getBackCamera();
        final int backCameraId = backCamera.second;
        mBackCameraInfo = backCamera.first;
        mCamera = Camera.open(backCameraId);
        cameraDisplayRotation();

        try {
            mCamera.setPreviewTexture(surface);
            mCamera.startPreview();
        } catch (IOException ioe) {
            // Something bad happened
        }
    }

    public void cameraDisplayRotation() {
        final int rotation = mActivity.getWindowManager()
                .getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        final int displayOrientation = (mBackCameraInfo.orientation
                - degrees + 360) % 360;
        mCamera.setDisplayOrientation(displayOrientation);
    }

    private Pair<Camera.CameraInfo, Integer> getBackCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        final int numberOfCameras = Camera.getNumberOfCameras();

        for (int i = 0; i < numberOfCameras; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return new Pair<Camera.CameraInfo, Integer>(cameraInfo,
                        Integer.valueOf(i));
            }
        }
        return null;
    }

    public boolean isCameraOpen() {
        return mCamera != null;
    }
}
