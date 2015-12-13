package letsencrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class LetsEncryptServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LetsEncryptServlet.class.getName());

    private static final String PATH = "/WEB-INF/letsencrypt/";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        if (!uri.startsWith("/.well-known/acme-challenge/")) {
            resp.sendError(404);
            return;
        }
        String challenge = uri.substring("/.well-known/acme-challenge/".length());
        challenge = sanitizePath(challenge);
        String res = PATH + challenge;

        try {
            String respString = readResource(res);
            resp.setContentType("text/plain");
            resp.getOutputStream().print(respString.trim());
        } catch (Throwable e) {
            log.severe("challenge not found: " + res);
            resp.sendError(404);
        }
    }

    private String sanitizePath(String s) {
        return s.replaceAll("[^a-zA-Z0-9-]+", "_");
    }

    private String readResource(String resName) throws IOException {
        log.info("reading " + resName);
        InputStream in = getServletContext().getResourceAsStream(resName);
        return inputStreamToString(in);
    }

    private static String inputStreamToString(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        return sb.toString();
    }
}
