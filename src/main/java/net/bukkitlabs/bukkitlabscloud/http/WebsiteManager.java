package net.bukkitlabs.bukkitlabscloud.http;

import com.sun.net.httpserver.HttpServer;
import fi.iki.elonen.NanoHTTPD;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsiteManager {
    private static final String ROOT_PATH = "/http/dist/";
    private static final String ERROR_PAGE = "/http/dist/404/index.html";

    private Map<String, WebsiteHandler> websiteHandlers;

    public WebsiteManager() {
        websiteHandlers = new HashMap<>();
    }

    public void startWebsite(String path) {
        if (websiteHandlers.containsKey(path)) {
            System.out.println("Website '" + path + "' is already running.");
            return;
        }

        String fullPath = ROOT_PATH + path;
        Path websitePath = Paths.get(fullPath);

        if (!Files.exists(websitePath)) {
            System.out.println("Website '" + fullPath + "' does not exist.");
            return;
        }

        WebsiteHandler handler = new WebsiteHandler(fullPath);
        websiteHandlers.put(path, handler);
        handler.start();
        System.out.println("Started website '" + path + "'.");
    }

    public void stopWebsite(String path) {
        WebsiteHandler handler = websiteHandlers.get(path);
        if (handler == null) {
            System.out.println("Website '" + path + "' is not running.");
            return;
        }

        handler.stop();
        websiteHandlers.remove(path);
        System.out.println("Stopped website '" + path + "'.");
    }

    public void startAll() {
        for (String path : websiteHandlers.keySet()) {
            startWebsite(path);
        }
    }

    public void stopAll(){
        for(String path: websiteHandlers.keySet()){
            stopWebsite(path);
        }
    }
    private class WebsiteHandler extends NanoHTTPD {
        private String websitePath;

        private WebsiteHandler(String websitePath) {
            super(8080);
            this.websitePath = websitePath;
        }

        @Override
        public Response serve(IHTTPSession session) {
            String uri = session.getUri();
            String filePath = websitePath + uri;

            Path requestedPath = Paths.get(filePath);
            if (Files.exists(requestedPath) && !Files.isDirectory(requestedPath)) {
                try {
                    return newFixedLengthResponse(Response.Status.OK, getMimeType(filePath), Files.newInputStream(requestedPath), Files.size(requestedPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_HTML, getErrorPageContent());
        }

        private String getMimeType(String filePath) {
            if (filePath.endsWith(".html") || filePath.endsWith(".htm")) {
                return NanoHTTPD.MIME_HTML;
            } else if (filePath.endsWith(".css")) {
                return "text/css";
            } else if (filePath.endsWith(".js")) {
                return "text/javascript";
            } else if (filePath.endsWith(".png")) {
                return "image/png";
            } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
                return "image/jpeg";
            } else if (filePath.endsWith(".gif")) {
                return "image/gif";
            } else {
                return NanoHTTPD.MIME_PLAINTEXT;
            }
        }

        private String getErrorPageContent() {
            String errorPagePath = ROOT_PATH + "404/index.html";
            try {
                return new String(Files.readAllBytes(Paths.get(errorPagePath)));
            } catch (IOException e) {
                e.printStackTrace();
                return "404 - Page not found.";
            }
        }

        public void start() {
            try {
                start(NanoHTTPD.SOCKET_READ_TIMEOUT,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            stop();
        }
    }

}
