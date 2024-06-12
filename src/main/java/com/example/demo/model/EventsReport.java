package com.example.demo.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@XmlRootElement
public class EventsReport {
	List<ScheduleEvent> scheduleEventList;
}
