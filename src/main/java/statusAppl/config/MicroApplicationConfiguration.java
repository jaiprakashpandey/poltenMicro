package statusAppl.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Jai on 27/04/2018.
 */
public class MicroApplicationConfiguration extends Configuration {
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory(); /* @JsonProperty public String getMongoHost() { return mongoHost; } @JsonProperty public void setMongoHost(String mongoHost) { this.mongoHost = mongoHost; } @JsonProperty public int getMongoPort() { return mongoPort; } @JsonProperty public void setMongoPort(int mongoPort) { this.mongoPort = mongoPort; } @JsonProperty public String getMongoDB() { return mongoDB; } @JsonProperty public void setMongoDB(String mongoDB) { this.mongoDB = mongoDB; } @JsonProperty public String getCollectionName() { return collectionName; } @JsonProperty public void setCollectionName(String collectionName) { this.collectionName = collectionName; } private String mongoDB; private String collectionName;*/

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
}