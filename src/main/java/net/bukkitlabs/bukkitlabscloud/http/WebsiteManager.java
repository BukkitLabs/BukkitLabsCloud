package net.bukkitlabs.bukkitlabscloud.http;

import com.sun.net.httpserver.HttpServer;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WebsiteManager {
    private HttpServer server;
    private List<String> runningWebsites;

    public WebsiteManager() {
        runningWebsites = new ArrayList<>();
    }

    public void startWebsite(String path) {
        if (!runningWebsites.contains(path)) {
            try {
                int port = 8080;
                server = HttpServer.create(new InetSocketAddress(port), 0);
                server.createContext("/", new WebsiteHandler());
                server.start();
                runningWebsites.add(path);
                System.out.println("Webserver started on port " + port + " for website: " + path);
            } catch (IOException e) {
                System.err.println("Failed to start the webserver for website: " + path);
                e.printStackTrace();
            }
        }
    }

    public void stopWebsite(String path) {
        if (runningWebsites.contains(path)) {
            runningWebsites.remove(path);
            System.out.println("Website stopped: " + path);
            if (runningWebsites.isEmpty()) {
                server.stop(0);
                server = null;
                System.out.println("Webserver stopped.");
            }
        }
    }

    public void startAll() {
        try {
            Files.walk(Paths.get("http/dist"))
                    .filter(Files::isDirectory)
                    .forEach(dir -> startWebsite(dir.toString()));
        } catch (IOException e) {
            System.err.println("Failed to start all websites.");
            e.printStackTrace();
        }
    }

    public void stopAll() {
        List<String> websitesToStop = new ArrayList<>(runningWebsites);
        websitesToStop.forEach(this::stopWebsite);
    }

}
