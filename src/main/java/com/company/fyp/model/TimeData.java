package com.company.fyp.model;

import java.time.LocalTime;

public class TimeData {

    private LocalTime time;
    private float kiloWatts;

    public TimeData(LocalTime time, float kiloWatts ) {
        this.time = time;
        this.kiloWatts = kiloWatts;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public float getKiloWatts() {
        return kiloWatts;
    }

    public void setKiloWatts( float kiloWatts ) {
        this.kiloWatts = kiloWatts;
    }
}
