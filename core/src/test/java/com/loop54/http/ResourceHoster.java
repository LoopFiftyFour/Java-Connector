package com.loop54.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;

public class ResourceHoster implements AutoCloseable {
    private HttpServer listener;

    public String calledPath;
    public Map<String, List<String>> calledHeaders;
    public String resourceString;
    public int statusCode;
    public int port;

    private static int getFreeTcpPort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException ioe) {
                // ignore this; if the port is still taken, let HttpServer.create throw later
            }
            return port;
        } catch (IOException ioe) {
            throw new IllegalStateException("No TCP port available", ioe);
        }  finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    throw new IllegalStateException("No TCP port available", ioe);
                }
            }
        }
    }

    public void start() {
        port = getFreeTcpPort();

        try {
            listener = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException ioe) {
            throw new IllegalStateException("Failed to set up HTTP server", ioe);
        }

        listener.createContext("/", exchange -> listenerCallback(exchange));
        listener.setExecutor(null);
        listener.start();
    }

    public void stop() {
        listener.stop(0);
    }

    void listenerCallback(HttpExchange exchange) {
        calledPath = exchange.getRequestURI().getRawPath();
        calledHeaders = exchange.getRequestHeaders();

        try {
            exchange.sendResponseHeaders(statusCode, resourceString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(resourceString.getBytes());
            os.close();
        } catch (IOException ioe) {
            throw new IllegalStateException("HTTP server callback failed", ioe);
        }
    }

    @Override public void close() {
        stop();
    }
}
