package IT;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import statusAppl.app.MicroServiceApplication;
import statusAppl.config.MicroApplicationConfiguration;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;

/**
 * Created by K316940 on 5/2/2018.
 */
public class VehiclesStatusMicroServiceIntegrationTest {
    @Rule
    public final DropwizardAppRule<MicroApplicationConfiguration> RULE =
            new DropwizardAppRule<MicroApplicationConfiguration>(MicroServiceApplication.class,
                    ResourceHelpers.resourceFilePath("testService.yaml"));

    @Test
    public void runServerTest() {
        Client client = new JerseyClientBuilder().build();
        Response µ_ServiceResponse = client.target(
                String.format("http://localhost:8585/status", RULE.getLocalPort())
        ).request().get(Response.class);

        assertNotNull(µ_ServiceResponse);


        Assert.assertEquals(200, µ_ServiceResponse.getStatus());
        assertNotNull(µ_ServiceResponse.getEntity());
        Object array = µ_ServiceResponse.getEntity();
        assertNotNull(array);
    }
}
