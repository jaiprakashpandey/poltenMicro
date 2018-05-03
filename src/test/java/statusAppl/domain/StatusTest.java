package statusAppl.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by K316940 on 5/2/2018.
 */
public class StatusTest {

    @Test
    public void verifySTatusCodes() {
        assertThat(Status.CONNECTED.getStatusCode(), is("CON"));
        assertThat(Status.DISCONNECTED, not("CON"));
        assertThat(Status.DISCONNECTED.getStatusCode(), is("DC"));
        assertThat(Status.CONNECTED, not("DC"));
    }
}