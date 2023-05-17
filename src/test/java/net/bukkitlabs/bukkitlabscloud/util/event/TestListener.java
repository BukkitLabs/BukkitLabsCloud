package net.bukkitlabs.bukkitlabscloud.util.event;

public class TestListener implements Listener {

    @Callable
    public void onTest(TestEvent event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
    }

    @Callable
    public void onTest1(CancelableTestEvent event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
        event.setCanceled(true);
    }

    @Callable(ignoreCancelled = true)
    public void onTest2(AnotherCancelableTestEvent event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
        event.setCanceled(true);
    }

    @Callable(ignoreCancelled = true)
    public void onTest3(AnotherCancelableTestEvent event) {
        event.setTestValue(event.getTestValue().equals("Test") ? "TRUE" : "FALSE");
        event.setCanceled(true);
    }
}
