package net.bukkitlabs.bukkitlabscloud.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import net.bukkitlabs.bukkitlabscloud.util.logger.Logger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebsiteHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        String requestedPath=exchange.getRequestURI().getPath();
        String fullPath=getWebsitePath(requestedPath);
        byte[] response=Files.readAllBytes(Paths.get(fullPath));

        String mimeType=getMimeType(fullPath);
        exchange.getResponseHeaders().set("Content-Type",mimeType);

        exchange.sendResponseHeaders(200,response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    private String getWebsitePath(String path){
        String fullPath="http/dist"+path;
        if(Files.exists(Paths.get(fullPath))){
            return fullPath;
        }else{
            return "http/dist/404/index.html";
        }
    }
    private String getMimeType(String filePath) {
        if (filePath.endsWith(".html") || filePath.endsWith(".htm")) {
            return "text/html";
        } else if (filePath.endsWith(".css")) {
            return "text/css";
        } else if (filePath.endsWith(".js")) {
            return "application/javascript";
        } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filePath.endsWith(".png")) {
            return "image/png";
        } else if (filePath.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }
}
