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
public class MigrateHealthCheck extends HealthCheck {
    private final Client client;

    public MigrateHealthCheck(Client client) {
        super();
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        WebTarget webTarget = client.target("http://localhost:8080/status/migrate");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        boolean isHealthy = false;
        Response response = invocationBuilder.get();
        @SuppressWarnings("rawtypes")
        String respString = response.readEntity(String.class);
        if (respString != null && respString.equalsIgnoreCase("{ \"status\": \"DATA MIGRATED\"}")) {
            isHealthy = true;
        }
        if (isHealthy) {
            webTarget = client.target("http://localhost:8080/status/clear");
            invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            String clearResp = response.readEntity(String.class);
            if ("{ \"status\": \"Test Data Deleted\"}".equalsIgnoreCase(clearResp)) {
                return Result.healthy();
            }
        }

        return Result.unhealthy("API Failed");
    }
}
