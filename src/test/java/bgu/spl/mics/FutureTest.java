package bgu.spl.mics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {

Future F;
Future F2;

    /*
    should build a Future obj.
     */
    @Before
    public void setUp() throws Exception {
        F = new Future();
        F2 = new Future();
    }

    @After
    public void tearDown() throws Exception {
    }


    /*
    this should take into consideration synchronizing- this is a block method because it waits for the resolving of the object.
    the test should check that it does not return an empty answer (before it resolved) and to check that the resolved answer is correct.
     */
    @Test
    public void get() {
        String result = "test";
        int num = 5;
        F.resolve(result);
        F2.resolve(num);
        assertEquals ("test", F.get());
        assertEquals (5 , F2.get());
    }

    /*
    this func' is used by the resolving service and it return the resolved value into the Future object to be returned to the origin service.
     */
    @Test
    public void resolve() {

        assertNull(F.get(10 , TimeUnit.MILLISECONDS));
        String result= "test";
        F.resolve(result);

        assertEquals ("test", F.get());
    }

    /*
    simpale test- Done is false as long as the object isnt resolved.
     */
    @Test
    public void isDone() {
        assertFalse(F.isDone());
        assertFalse(F2.isDone());
        String result= "test";
        F.resolve(result);
        int num = 3;
        F2.resolve(num);
        assertTrue(F.isDone());
        assertTrue(F2.isDone());
    }

    /*
    another getter only this one MUST not be synchronized- it uses time lapse- which mean it use as a getter- gives the
    result if solved, if not- it would wait a given time if the object is resolved during this time- it would return it
    other wise gives null.
    the test should check a return of unresolvable object (one that never been sent to other service for example )
     */
    @Test
    public void get1() {
        String result = "test";
        int num = 5;
        assertNull(F.get(10 , TimeUnit.SECONDS));
        assertNull(F2.get(10 , TimeUnit.SECONDS)); // does the code continue after get is put to sleep? or will it wait?
        F.resolve(result);
        F2.resolve(num);
    }
}