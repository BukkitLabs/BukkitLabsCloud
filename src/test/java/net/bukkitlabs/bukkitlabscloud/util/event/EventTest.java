package net.bukkitlabs.bukkitlabscloud.util.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    private final EventHandler eventHandler = new EventHandler();

    @Test
    void testCallEvent() throws EventCannotBeProcessedException {
        eventHandler.registerListener(new TestListener());

        final TestEvent testEvent1 = new TestEvent("Test");
        final TestEvent testEvent2 = new TestEvent("Hello");

        eventHandler.call(testEvent1);
        eventHandler.call(testEvent2);

        assertEquals("TRUE", testEvent1.getTestValue());
        assertEquals("FALSE", testEvent2.getTestValue());
    }

    @Test
    void testCallEventCancel() throws EventCannotBeProcessedException {
        eventHandler.registerListener(new TestListener());

        final CancelableTestEvent testEvent1 = new CancelableTestEvent("Test");
        final AnotherCancelableTestEvent testEvent2 = new AnotherCancelableTestEvent("Test");

        eventHandler.call(testEvent1);
        eventHandler.call(testEvent2);

        assertEquals("TRUE", testEvent1.getTestValue());
        assertEquals("FALSE", testEvent2.getTestValue());
    }
}
