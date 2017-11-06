package com.oluwadara.youtrackmobile.app.data.model;


import java.util.Random;

public class CellInfo extends Model {
    private int mnc;
    private int mcc;
    private int lac;
    private int cid;
    private String date;

    public CellInfo() {
    }

    public CellInfo(int mnc, int mcc, int lac, int cid) {
        this.mnc = mnc;
        this.mcc = mcc;
        this.lac = lac;
        this.cid = cid;
        Random randomGenerator = new Random();
        int id = randomGenerator.nextInt(100000);
        setId(String.valueOf(id));

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(int mnc) {
        this.mnc = mnc;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
