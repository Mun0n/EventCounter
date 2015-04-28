package com.hrules.eventcounter;


import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import java.util.Map;
import org.junit.Before;

public class EventCounterTest extends AndroidTestCase {
    private final static String TAG = "EventCounterTest";

    private final static String DEFAULT_KEY_NAME = "DEFAULT_KEY_NAME";
    private static final int DEFAULT_VALUE = 5;

    private Context context;
    private EventCounter eventCounter;

    @Before
    public void setUp() {
        context = getContext();
        eventCounter = new EventCounter(context);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        eventCounter.removeAll();
    }

    @SmallTest
    public void testSetKeyValue() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == DEFAULT_VALUE);
    }

    @SmallTest
    public void testSingletonSetKeyValue() {
        EventCounter.with(context).setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        assertTrue(EventCounter.with(context).getKeyValue(DEFAULT_KEY_NAME) == DEFAULT_VALUE);
    }

    @SmallTest
    public void testGetDefaultKeyValue() {
        eventCounter.removeKey(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testGetKeyValue() {
        assertTrue(new EventCounter(context).getKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE) == DEFAULT_VALUE);
    }

    @SmallTest
    public void testRemoveKey() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.removeKey(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testAddKey() {
        eventCounter.addKey(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testResetKeyValue() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.resetKeyValue(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.getKeyValue(DEFAULT_KEY_NAME) == EventCounter.DEFAULT_KEY_VALUE);
    }

    @SmallTest
    public void testCheckKeyValue() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE));
    }

    @SmallTest
    public void testCheckKeyValueEqualTo() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE, EventCounter.EQUAL_TO));
    }

    @SmallTest
    public void testCheckKeyValueGreateThan() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE + 1);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE, EventCounter.GREATER_THAN));
    }

    @SmallTest
    public void testCheckKeyValueGreateThanOrEqualTo() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE, EventCounter.GREATER_THAN_OR_EQUAL_TO));
    }

    public void testCheckKeyValueLessThan() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE - 1);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE, EventCounter.LESS_THAN));
    }

    public void testCheckKeyValueLessThanOrEqualTo() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE, EventCounter.LESS_THAN_OR_EQUAL_TO));
    }

    @SmallTest
    public void testincrementByOne() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.incrementKeyValue(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE + EventCounter.DEFAULT_INCREMENT_VALUE));
    }

    @SmallTest
    public void testincrementByMax() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.incrementKeyValue(DEFAULT_KEY_NAME, Integer.MAX_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, Integer.MAX_VALUE));
    }

    @SmallTest
    public void testdecrementByOne() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.decrementKeyValue(DEFAULT_KEY_NAME);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE - EventCounter.DEFAULT_DECREMENT_VALUE));
    }

    @SmallTest
    public void testdecrementByMax() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.decrementKeyValue(DEFAULT_KEY_NAME, Integer.MAX_VALUE);
        assertTrue(eventCounter.checkKeyValue(DEFAULT_KEY_NAME, EventCounter.DEFAULT_KEY_VALUE));
    }

    @SmallTest
    public void testRemoveAll() {
        eventCounter.removeAll();
        Map<String, ?> map = eventCounter.getAll();
        assertTrue(map.size() == 0);
    }

    @SmallTest
    public void testGetAll() {
        eventCounter.addKey(DEFAULT_KEY_NAME);
        Map<String, ?> map = eventCounter.getAll();
        assertTrue(map.size() != 0);
    }

    @SmallTest
    public void testContains() {
        assertFalse(eventCounter.contains(DEFAULT_KEY_NAME));
    }

    @SmallTest
    public void testResetAll() {
        eventCounter.setKeyValue(DEFAULT_KEY_NAME, DEFAULT_VALUE);
        eventCounter.resetAll();
        assertTrue(eventCounter.contains(DEFAULT_KEY_NAME));
    }
}
