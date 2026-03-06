package com.example.POJO;

public class trajectoryWithBLOBs extends trajectory {
    private byte[] pathx;

    private byte[] pathy;

    public byte[] getPathx() {
        return pathx;
    }

    public void setPathx(byte[] pathx) {
        this.pathx = pathx;
    }

    public byte[] getPathy() {
        return pathy;
    }

    public void setPathy(byte[] pathy) {
        this.pathy = pathy;
    }


}