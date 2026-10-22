package com.loop54.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

class ResourceHoster implements AutoCloseable {
    private HttpServer listener;

    String calledPath;
    Map<String, List<String>> calledHeaders;
    String resourceString;
    int statusCode;
    int port;

    private static int getFreeTcpPort() {
        try (var socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (final IOException ioe) {
            throw new IllegalStateException("No TCP port available", ioe);
        }
    }

    void start() {
        port = getFreeTcpPort();

        try {
            listener = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (final IOException ioe) {
            throw new IllegalStateException("Failed to set up HTTP server", ioe);
        }

        listener.createContext("/", this::listenerCallback);
        listener.setExecutor(null);
        listener.start();
    }

    void stop() {
        listener.stop(0);
    }

    private void listenerCallback(final HttpExchange exchange) {
        calledPath = exchange.getRequestURI().getRawPath();
        calledHeaders = exchange.getRequestHeaders();

        final var body = resourceString.getBytes(StandardCharsets.UTF_8);
        try {
            exchange.sendResponseHeaders(statusCode, body.length);
            try (final var os = exchange.getResponseBody()) {
                os.write(body);
            }
        } catch (final IOException ioe) {
            throw new IllegalStateException("HTTP server callback failed", ioe);
        }
    }

    @Override public void close() {
        stop();
    }
}
