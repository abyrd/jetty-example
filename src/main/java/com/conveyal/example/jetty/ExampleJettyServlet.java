package com.conveyal.example.jetty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by abyrd on 2019-10-19
 */
public class ExampleJettyServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the wildcard * part of the path. Servlet does not support path params, you have to parse them out.
        // https://stackoverflow.com/a/8715566
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length == 3) {
            // Leading slash makes part 0 empty
            resp.getOutputStream().write(pathParts[1].getBytes(StandardCharsets.UTF_8));
            resp.getOutputStream().write(pathParts[2].getBytes(StandardCharsets.UTF_8));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

}
