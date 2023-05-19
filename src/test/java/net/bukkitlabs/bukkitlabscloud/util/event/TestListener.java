package net.bukkitlabs.bukkitlabscloud.util.event;

public class TestListener implements Listener {

    @PacketCatch
    public void onTest(TestPacket event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
    }

    @PacketCatch
    public void onTest1(CancelableTestPacket event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
        event.setCanceled(true);
    }

    @PacketCatch(ignoreCancelled = true)
    public void onTest2(AnotherCancelableTestPacket event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
        event.setCanceled(true);
    }

    @PacketCatch(ignoreCancelled = true)
    public void onTest3(AnotherCancelableTestPacket event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
        event.setCanceled(true);
    }
}
