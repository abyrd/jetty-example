package com.conveyal.example.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import java.io.IOException;

/**
 * Demonstrates use of Jetty for a small ( heap, JAR, dependency JARs)
 * HTTP API server, using no annotations, injection, XML, "enterprise beans", etc.
 */
public class JettyMain {

    /**
     * The top-level handlers you want are probably Handlers.routing() and Handlers.pathTemplate()
     *
     * <p> Try hitting these endpoints:
     * <pre>
     * http://localhost:8080/echo/cheese/cake
     * curl -X POST http://localhost:8080/echo/destination --data '{"a":1}'
     * curl -v -X POST 'http://localhost:8080/echo/destination' --data '{"a":1}'
     * curl -s -H 'Accept-Encoding: gzip' 'http://localhost:8080/json/a/b' | gunzip
     * </pre>
     *
     * Routing format here:
     * https://stackoverflow.com/a/39911796/778449
     * For some reason the path parameters end up in the queryParameters map, not the pathParameters one.
     *
     *
     * Try -Xmx16M or even 10G, with
     * <pre>
     * ab -k -n 1000000 -c 10 -H 'Accept-Encoding: gzip' 'http://localhost:8080/json/a/b'
     * </pre>
     */
    public static void main(String[] args) throws Exception {
        int port = 8080;
        Server server = new Server(port);
        server.setHandler(new ExampleJettyHandler());
        server.start();
        server.join();

//        ServletContextHandler context = new ServletContextHandler();
//        context.setContextPath("/hello");
//        context.addServlet(HelloServlet.class, "/");
//
//        HandlerCollection handlers = new HandlerCollection();
//        handlers.setHandlers(new Handler[]{context, new DefaultHandler()});

        server.setHandler(new ExampleJettyHandler());
        server.start();
        server.join();
    }

    private static void injectDependencies() {

    }

}
