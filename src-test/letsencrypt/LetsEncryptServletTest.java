package letsencrypt;

import app.test.BaseJettyTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class LetsEncryptServletTest extends BaseJettyTest {

    @Before
    public void setUp() throws Exception {
        startServer("http://127.0.0.1", 8080);
    }

    @Test
    public void testShouldReturnCorrectResponse() {
        String expected = "NQxc6h4RKTQ-4gVaqwsCaZZqhZTMilPtr3mdCVcBbtU.ex1dnALARXVVaYQexTYm7EJgkl43OslrZr3_1kzp4ex";
        String challenge = "NQxc6h4RKTQ-4gVaqwsCaZZqhZTMilPtr3mdCVcBbex";

        String resp = target.path("/.well-known/acme-challenge/" + challenge)
            .request()
            .get(String.class);
        log.info(resp);
        assertEquals(expected, resp);
    }

    @Test
    public void testShouldReturn404() {
        String challenge = "NQxc6h4RKTQ-4gVaqwsCaZZqhZTMilPtr3mdCVcBbeZ";

        boolean thrown = false;
        try {
            target.path("/.well-known/acme-challenge/" + challenge)
                .request()
                .get(String.class);
        } catch (NotFoundException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
