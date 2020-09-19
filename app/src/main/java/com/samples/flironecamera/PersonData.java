package com.samples.flironecamera;

import com.google.android.gms.vision.face.Face;

public class PersonData {
    private Face face;
    private double temp;

    public PersonData(Face face, double temp) {
        this.face = face;
        this.temp = temp;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
