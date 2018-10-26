package com.javarunner.materialdesign.ui.adapter;

public class ListEvent {
    public enum EventType {CLICK, LONG_CLICK, TOGGLE}

    private EventType eventType;
    private Integer position;

    public ListEvent(EventType eventType, Integer position) {
        this.eventType = eventType;
        this.position = position;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Integer getPosition() {
        return position;
    }
}
