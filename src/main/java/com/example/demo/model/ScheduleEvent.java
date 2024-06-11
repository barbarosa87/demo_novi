package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScheduleEvent {
    private String market;
    private String event;
    private String url;
    private String timeUntilStart;
}
