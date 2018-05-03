package statusAppl.app;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import statusAppl.config.MicroApplicationConfiguration;
import statusAppl.db.AddressDao;
import statusAppl.db.CustomerDao;
import statusAppl.db.VehicleDao;
import statusAppl.db.entity.AddressDto;
import statusAppl.db.entity.CustomerDto;
import statusAppl.db.entity.VehicleDto;
import statusAppl.health.ClearTestDataHealthCheck;
import statusAppl.health.MigrateHealthCheck;
import statusAppl.health.StatusServiceHealthCheck;
import statusAppl.restapi.VehiclesMicroServiceResource;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.ws.rs.client.Client;
import java.util.EnumSet;

/**
 * Created by Jai on 27/04/2018.
 */
public class MicroServiceApplication extends Application<MicroApplicationConfiguration> {
    public static void main(String[] args) throws Exception {
        new MicroServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "Polten Vehilces status track Application";
    }

    private final HibernateBundle<MicroApplicationConfiguration> hibernateBundle = new HibernateBundle<MicroApplicationConfiguration>(VehicleDto.class, CustomerDto.class, AddressDto.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(MicroApplicationConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void run(MicroApplicationConfiguration microAppConfigs, Environment env) throws Exception {
        final VehicleDao vDao = new VehicleDao(hibernateBundle.getSessionFactory());
        final AddressDao aDao = new AddressDao(hibernateBundle.getSessionFactory());
        final CustomerDao cDao = new CustomerDao(hibernateBundle.getSessionFactory());
        env.jersey().register(new VehiclesMicroServiceResource(vDao, aDao, cDao));

        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                env.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        //Health checks

        final Client client = new JerseyClientBuilder(env).build("RESTClient");
        env.healthChecks().register("StatusServiceHealthCheck", new StatusServiceHealthCheck(client));
        env.healthChecks().register("MigrateServiceHealthCheck", new MigrateHealthCheck(client));
        env.healthChecks().register("ClearTestDataHealthCheck", new ClearTestDataHealthCheck(client));

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

    @Override
    public void initialize(Bootstrap<MicroApplicationConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
        bootstrap.addBundle(new MigrationsBundle<MicroApplicationConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MicroApplicationConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
    }
}