package statusAppl.domain;

/**
 * Created by Jai on 4/28/2018.
 */
public enum Status {
    CONNECTED("CON"), DISCONNECTED("DC");
    private String statusCode;

    Status(String status) {
        this.statusCode = status;
    }

    public String getStatusCode() {
        return statusCode;
    }
}