package bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Yahel on 25/11/2018.
 */
public class MessageBusTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /*
    method called by a service - void func' get as param the type and the service.
    should check if the callback of the service was "plant" in the message bus for the right type for later use.
    THE SUBSCRIPTION IS MADE BY MAKING A CALLBACK AT THE BUS FOR THE RIGHT TYPE OF MESSAGE.
    the test should subscribe and send a message of the right type and check if the callback is "triggered"
    the service has its own protected subscribe func' that send call back and type, which should call this func'
    the service function return Event <T> because of the callback.
    it might be over doing - because it already check the send event func'

    update - the bus should have a field by type of message that hold which services are subscribed (LinkedList can do the work)
     */
    @Test
    public void subscribeEvent() throws Exception {

    }

    /*
    pretty much like the subscribeEvent, but with a broadcast.
    the test should have at least 2 services and use 1 broadcast- check if both callback were "triggered"
    it might be over doing - because it already check the send Broadcast func'
     */
    @Test
    public void subscribeBroadcast() throws Exception {

    }
/*
a second func' - used after a service use his own complete function, get the event and result from the service complete fun'
should resolve the event that the service completed (using the future object at another service- object that need to be update at completion)
the test should check if the future object at the origin service (the one that created the Future and the message) was updated
completed service - the service that completed the event
origin service - the service that made the event that was in need of compeltion.
 */
    @Test
    public void complete() throws Exception {

    }

    /*
    this test is quit similar to subscribe to broadcast. should check that the last event in all the services that are subscribed is the broadcast
    when subscribe we checked if the call back works by sending broadcast, might be over doing which made this test useless.
     */
    @Test
    public void sendBroadcast() throws Exception {

    }
/*
as with send broadcast.
 */
    @Test
    public void sendEvent() throws Exception {

    }
/*
the func' create a queue and add it to the service.
the test should create the queue and use it (insert and remove- by using send event) to see that the queue is under the service.
the test MUST check if the service is unregistered
 */
    @Test
    public void register() throws Exception {

    }
/*
should clean the queue of service m , then delete the queue, then delete every message that has a reference to m (by call backs maybe?)
the test should send an event created by the service m to the message bus, and inserted into the queue of another service. and then unregister m
and check if the said event was cleared.
the test MUST check that the service is register and if it isn't do nothing.
 */
    @Test
    public void unregister() throws Exception {

    }
/*
function for the service to get a new message from the queue- from the bus point of view it should give the next message.
if the service is not registered it MUST thorw and exception  illegal state
 */
    @Test
    public void awaitMessage() throws Exception {

    }

}