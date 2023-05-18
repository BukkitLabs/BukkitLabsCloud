package net.bukkitlabs.bukkitlabscloud.util.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketTest {

    private final PacketHandler packetHandler = new PacketHandler();

    @Test
    void testCallEvent() throws PacketCannotBeProcessedException {
        packetHandler.registerListener(new TestListener());

        final TestPacket testEvent1 = new TestPacket("Test");
        final TestPacket testEvent2 = new TestPacket("Hello");

        packetHandler.call(testEvent1);
        packetHandler.call(testEvent2);

        assertEquals("TRUE", testEvent1.getTestValue());
        assertEquals("FALSE", testEvent2.getTestValue());
    }

    @Test
    void testCallEventCancel() throws PacketCannotBeProcessedException {
        packetHandler.registerListener(new TestListener());

        final CancelableTestPacket testEvent1 = new CancelableTestPacket("Test");
        final AnotherCancelableTestPacket testEvent2 = new AnotherCancelableTestPacket("Test");

        packetHandler.call(testEvent1);
        packetHandler.call(testEvent2);

        assertEquals("TRUE", testEvent1.getTestValue());
        assertEquals("FALSE", testEvent2.getTestValue());
    }
}
