package com.nwodhcout.napper.app;

import java.io.Serializable;

/**
 * Created by Pete on 10.7.2014.
 */
public class Alarm {
    private long startTime;
    private long napTime; //seconds

    public Alarm(long start, long napTime){
        this.startTime = start;
        this.napTime = napTime;
    }

    public long getStartTime(){
        return this.startTime;
    }

    public long getNapTime(){
        return this.napTime;
    }
}
