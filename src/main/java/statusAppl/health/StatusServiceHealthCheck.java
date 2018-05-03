package statusAppl.health;

import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by K316940 on 5/3/2018.
 */
public class StatusServiceHealthCheck extends HealthCheck {
    private final Client client;

    public StatusServiceHealthCheck(Client client) {
        super();
        this.client = client;
    }

    @Override
    protected Result check() throws Exception {
        boolean isHealthy = false;
        WebTarget webTarget = client.target("http://localhost:8080/status");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        @SuppressWarnings("rawtypes")
        ArrayList statusList = response.readEntity(ArrayList.class);
        if (statusList != null && statusList.size() == 0) {
            isHealthy = true;
        }
        if (isHealthy) {
            webTarget = client.target("http://localhost:8080/status/migrate");
            invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            String respString = response.readEntity(String.class);
            if (respString != null && respString.equalsIgnoreCase("{ \"status\": \"DATA MIGRATED\"}")) {
                isHealthy = true;
            } else {
                isHealthy = false;
            }
        }
        if (isHealthy) {
            webTarget = client.target("http://localhost:8080/status");
            invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            statusList = response.readEntity(ArrayList.class);
            if (statusList != null && statusList.size() > 0) {
                isHealthy = true;
            } else {
                isHealthy = false;
            }
        }
        if (isHealthy) {
            webTarget = client.target("http://localhost:8080/status/clear");
            invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            String clearResp = response.readEntity(String.class);
            if ("{ \"status\": \"Test Data Deleted\"}".equalsIgnoreCase(clearResp)) {
                isHealthy = true;
            } else {
                isHealthy = false;
            }
        }
        if (isHealthy) {
            webTarget = client.target("http://localhost:8080/status/clear");
            invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();
            String clearResp = response.readEntity(String.class);
            if ("{ \"status\": \"Test Data already empty\"}".equalsIgnoreCase(clearResp)) {
                isHealthy = true;
            } else {
                isHealthy = false;
            }
        }

        if (isHealthy)
            return Result.healthy();

        return Result.unhealthy("API Failed");
    }
}
