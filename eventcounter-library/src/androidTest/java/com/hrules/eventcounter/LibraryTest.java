package com.hrules.eventcounter;


import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import java.util.Map;
import org.junit.Before;

public class LibraryTest extends AndroidTestCase {
    private final static String DEFAULT_KEY_NAME = "DEFAULT_KEY_NAME";

    private Context context;

    @Before
    public void setUp() {
        context = this.getContext();
    }

    @SmallTest
    public void testSetKeyValue() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == 5);
    }

    @SmallTest
    public void testSingletonSetKeyValue() {
        EventCounter.with(context).setKeyValue(DEFAULT_KEY_NAME, 5);
        assertTrue(EventCounter.with(context).getKeyValue(DEFAULT_KEY_NAME) == 5);
    }

    @SmallTest
    public void testGetDefaultKeyValue() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.removeKey(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testGetKeyValue() {
        assertTrue(new EventCounter(context).getKeyValue(DEFAULT_KEY_NAME, 5) == 5);
    }

    @SmallTest
    public void testRemoveKey() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.removeKey(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testAddKey() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.removeKey(DEFAULT_KEY_NAME);
        eventCounter.addKey(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testResetKeyValue() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.resetKeyValue(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testCheckKeyValue() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, 5));
    }

    @SmallTest
    public void testincrementByOne() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.incrementKeyValue(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, 5 + EventCounter.DEFAULT_INCREMENT_VALUE));
    }

    @SmallTest
    public void testincrementByMax() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.incrementKeyValue(DEFAULT_KEY_NAME, Integer.MAX_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, Integer.MAX_VALUE));
    }

    @SmallTest
    public void testdecrementByOne() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.decrementKeyValue(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, 5 - EventCounter.DEFAULT_DECREMENT_VALUE));
    }

    @SmallTest
    public void testdecrementByMax() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.decrementKeyValue(DEFAULT_KEY_NAME, Integer.MAX_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, EventCounter.DEFAULT_KEY_VALUE));
    }

    @SmallTest
    public void testRemoveAll() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.removeAll();
        Map<String, ?> map = eventCounter.getAll();
        assertTrue(map.size() == 0);
    }

    @SmallTest
    public void testGetAll() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.removeAll();
        eventCounter.addKey(DEFAULT_KEY_NAME);
        Map<String, ?> map = eventCounter.getAll();
        assertTrue(map.size() != 0);
    }

    @SmallTest
    public void testContains() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.removeAll();
        assertFalse(eventCounter.contains(DEFAULT_KEY_NAME));
    }

    @SmallTest
    public void testResetAll() {
        EventCounter eventCounter = new EventCounter(context);
        eventCounter.removeAll();
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, 5);
        eventCounter.resetAll();
        assertTrue(eventCounter.contains(DEFAULT_KEY_NAME));
    }
}
