package statusAppl.health;

import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by K316940 on 5/3/2018.
 */
public class ClearTestDataHealthCheck extends HealthCheck {
    private final Client client;

    public ClearTestDataHealthCheck(Client client) {
        super();
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        WebTarget webTarget = client.target("http://localhost:8080/status/clear");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();
        String responseStr = response.readEntity(String.class);

        if ("{ \"status\": \"Test Data already empty\"}".equalsIgnoreCase(responseStr) || "{ \"status\": \"Test Data Deleted\"}".equalsIgnoreCase(responseStr)) {
            return Result.healthy();
        }
        return Result.unhealthy("API Failed");
    }
}
