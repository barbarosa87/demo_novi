Java version > 17
Task1 -> you can run scenario task1 from test.feature
Task2,3,4,5 -> you can run scenario task2345 from test.feature

The following properties exists at application.properties
live-programma.url=https://www.novibet.gr/live-programma 
stoixima-live.url=https://www.novibet.gr/stoixima-live
waittimeoutSeconds=20 //Timeout of selenium wait until



If you want to change the time that scenario task2345 is running and waiting for events to go live you need to change the step
-> And User waits 30 minutes and checking if events are delayed or dropped
and add you own time 

Produce report.xml for the final report 