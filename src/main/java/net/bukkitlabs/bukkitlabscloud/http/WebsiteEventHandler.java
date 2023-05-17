package net.bukkitlabs.bukkitlabscloud.http;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebsiteEventHandler extends Application{
    private WebView webView;
    private WebEngine webEngine;

    public WebsiteEventHandler(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        webView = new WebView();
        webEngine = webView.getEngine();

        webEngine.setOnAlert(this::handleJavaScriptEvent);

        Scene scene = new Scene(webView, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        webEngine.load(getClass().getResource("http/dist/index.html").toExternalForm());
    }

    private void handleJavaScriptEvent(WebEvent<String> event) {
        if ("clickEvent".equals(event.getData())) {
            System.out.println("Java code received a custom event from JavaScript!");
            //
        }
    }
}
