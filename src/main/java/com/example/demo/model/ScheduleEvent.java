package com.example.demo.model;

import com.example.demo.enums.EventsStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
public class ScheduleEvent {
    private String url;
    private String market;
    private Calendar startDate;
    private Calendar appearDate;
    private EventsStatus status;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ScheduleEvent that = (ScheduleEvent) o;
        return Objects.equals(url, that.url) && Objects.equals(market, that.market);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, market);
    }
}
