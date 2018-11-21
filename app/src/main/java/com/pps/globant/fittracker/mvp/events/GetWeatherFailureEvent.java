package com.pps.globant.fittracker.mvp.events;

public class GetWeatherFailureEvent {
    private Throwable t;

    public GetWeatherFailureEvent(Throwable t) {
        this.t = t;
    }

    public Throwable getT() {
        return t;
    }
}
