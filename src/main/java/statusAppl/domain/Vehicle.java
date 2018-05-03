package statusAppl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by k316940 on 4/30/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    private String vehicleId;
    private Date registrationDate;
    private String status;
    private String regNumber;
    private String ownerId;
    private String customerName;
    private String custAddr;

    public Vehicle() {
    }

    @JsonProperty
    public String getVehicleId() {
        return vehicleId;
    }

    @JsonProperty
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @JsonProperty
    public Date getRegistrationDate() {
        return registrationDate;
    }

    @JsonProperty
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonProperty
    public String getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty
    public String getRegNumber() {
        return regNumber;
    }

    @JsonProperty
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @JsonProperty
    public String getOwnerId() {
        return ownerId;
    }

    @JsonProperty
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty
    public String getCustomerName() {
        return customerName;
    }

    @JsonProperty
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @JsonProperty
    public String getCustAddr() {
        return custAddr;
    }

    @JsonProperty
    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr;
    }

    public Vehicle(String vehicleId, Date registrationDate, String status, String regNumber, String ownerId, String customerName, String custAddr) {
        this.vehicleId = vehicleId;
        this.registrationDate = registrationDate;
        this.status = status;
        this.regNumber = regNumber;
        this.ownerId = ownerId;
        this.customerName = customerName;
        this.custAddr = custAddr;
    }
}
