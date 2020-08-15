/*******************************************************************
 * @title FLIR THERMAL SDK
 * @file FrameDataHolder.java
 * @Author FLIR Systems AB
 *
 * @brief Container class that holds references to Bitmap images
 *
 * Copyright 2019:    FLIR Systems
 ********************************************************************/

package com.samples.flironecamera;

import android.graphics.Bitmap;

import com.flir.thermalsdk.image.ThermalImage;

class FrameDataHolder {

    public final Bitmap msxBitmap;
    public final Bitmap dcBitmap;
    public ThermalImage thermalImage;

    FrameDataHolder(Bitmap msxBitmap, Bitmap dcBitmap){
        this.msxBitmap = msxBitmap;
        this.dcBitmap = dcBitmap;
    }

    void setThermalImage(ThermalImage thermalImage) {
        this.thermalImage = thermalImage;
    }
}
