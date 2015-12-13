package app.test;

import app.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;

@Slf4j
public class BaseJettyTest {

    static {
        LogUtil.setJulFormat();
    }

    protected Server server = null;
    protected WebTarget target;
    protected int port;
    protected String host;

    protected String getServerUrl() {
        return String.format("%s:%s", host, port);
    }

    protected void startServer(String host, int port) throws Exception {
        if(server != null) return;

        this.host = host;
        this.port = port;

        server = new Server(port);

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setWar("war");

        log.info(">>> STARTING EMBEDDED JETTY SERVER "+getServerUrl());
        server.setHandler(bb);
        server.start();

        target = ClientBuilder.newClient().target(host+":"+port).path("/");
        log.info("\n\n\n");

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void waitForEnter(String msg) throws IOException {
        if(msg == null) msg = "Waiting for ENTER...";
        log.info("\n"+msg+"\n");
        System.in.read();
    }
    protected void waitForEnterUri(String uri) throws IOException {
        waitForEnter(getServerUrl() + uri);
    }

    protected void stopServer() throws Exception {
        log.info("\n\n\n");
        server.stop();
        server.join();
    }


}
