package com.example.demo;


import com.example.demo.model.EventsReport;
import com.example.demo.model.ScheduleEvent;
import com.example.demo.poms.LiveProgramPage;
import com.example.demo.poms.LiveStoiximaPage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.LongSupplier;


import static com.example.demo.enums.EventsStatus.*;

@Component
public class Actions {

	Logger logger = LoggerFactory.getLogger(Actions.class);
	@Autowired
	LiveProgramPage liveProgramPage;

	@Autowired
	LiveStoiximaPage liveStoiximaPage;

	public void openLiveProgramPage() {

		liveProgramPage.openPage();
	}

	public void quitLiveProgramBrowser() {

		liveProgramPage.quitDriver();
	}


	public void filterMarketList(String market) {

		liveProgramPage.filterMarkets(market);
	}


	public void openLiveStoiximaPage() {

		liveStoiximaPage.openPage();
	}

	public void quitLiveStoiximaBrowser() {

		liveStoiximaPage.quitDriver();
	}

	public void waitAndCheckEvents(int timeout) {
		int interval = 1000;
		long startTime = System.currentTimeMillis();
		LongSupplier timeSpent = () -> System.currentTimeMillis() - startTime;

		List<ScheduleEvent> eventList = liveProgramPage.getAllEvents();
		try {
            //List<ScheduleEvent> appearedEvents=new ArrayList<>();
			List<ScheduleEvent> goingLiveEvents = new ArrayList<>();
			while (timeSpent.getAsLong() < timeout * 60000L) {

				eventList.forEach(event -> {
					if (liveProgramPage.checkIFElementGoingLive(event) && !goingLiveEvents.contains(event)) {
						event.setStartDate(Calendar.getInstance());
						goingLiveEvents.add(event);
					}
				});
				if (!goingLiveEvents.isEmpty()) {
					goingLiveEvents.forEach(event -> {
						if (liveStoiximaPage.elemApears(event)) {

							logger.info("Event appeared {}",event.getUrl());
							event.setAppearDate(Calendar.getInstance());
							long timediff = (event.getAppearDate().getTimeInMillis() - event.getStartDate()
									.getTimeInMillis()) / 1000;
							if (timediff < 5) {
								event.setStatus(ontime);
							} else if (timediff < (20 * 60)) {
								event.setStatus(delayed);
							} else {
								event.setStatus(dropped);
							}
                            //appearedEvents.add(event);
						}
					});
                  /*  appearedEvents.forEach(event -> {
						int index=goingLiveEvents.indexOf(event);
						if (index>=0){
							logger.info("Remove event from goinglive list {}",event.getUrl());
							goingLiveEvents.remove(goingLiveEvents.indexOf(event));
						}
                    });
*/
				}

				logger.info("Appeared events {}",goingLiveEvents.size());
				Thread.sleep(interval);
			}

			writetoxml(goingLiveEvents);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}


	public void writetoxml(List<ScheduleEvent> scheduleEvents) throws JAXBException, FileNotFoundException {
		JAXBContext contextObj = JAXBContext.newInstance(EventsReport.class);

		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		EventsReport report=new EventsReport();
		report.setScheduleEventList(scheduleEvents);

		marshallerObj.marshal(report, new FileOutputStream("report.xml"));
	}




}
