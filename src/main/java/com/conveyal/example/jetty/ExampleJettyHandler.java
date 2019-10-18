package com.conveyal.example.jetty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Apparently Jetty manages to apply nonblocking NIO to Servlets: https://stackoverflow.com/a/25195392
 * Created by abyrd on 2019-10-19
 */
public class ExampleJettyHandler extends AbstractHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle (
        String target,
        Request baseRequest,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        bufferedAsyncJsonSend(new StringHolder("1", "2"), response);
        baseRequest.setHandled(true);
    }

    public static String parseJsonFromPostRequest (Request request) {
        try {
            final var tree = objectMapper.readTree(request.getInputStream());
            return tree.toPrettyString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * TODO Not yet async.
     */
    public static void bufferedAsyncJsonSend(Object object, HttpServletResponse response) {
        var objectMapper = new ObjectMapper();
        try {
            var jsonBytes = objectMapper.writeValueAsBytes(object);
            response.getOutputStream().write(jsonBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * An example data model class that we can serialize to JSON.
     */
    public static class StringHolder {
        public final String first;
        public final String second;

        public StringHolder(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

}
