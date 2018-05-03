package statusAppl.restapi;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.Data;
import statusAppl.db.AddressDao;
import statusAppl.db.CustomerDao;
import statusAppl.db.VehicleDao;
import statusAppl.db.entity.AddressDto;
import statusAppl.db.entity.CustomerDto;
import statusAppl.db.entity.VehicleDto;
import statusAppl.domain.Vehicle;
import statusAppl.util.StatusHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static statusAppl.util.StatusHelper.getAddrees;
import static statusAppl.util.StatusHelper.getCustomerName;

/**
 * Created by Jai on 27/04/2018.
 */
@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Data
public class VehiclesMicroServiceResource {
    List<VehicleDto> vehicleDtos;
    private VehicleDao dao;
    List<VehicleDto> currentVehicleAdded;
    AddressDao adDao;
    CustomerDao cDao;
    List<AddressDto> addressList = new ArrayList<>();
    List<CustomerDto> customers = new ArrayList<>();

    public VehiclesMicroServiceResource(VehicleDao dao, AddressDao adDao, CustomerDao cDao) {
        this.dao = dao;
        this.adDao = adDao;
        this.cDao = cDao;
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getVehicleStatus() {
        List<Vehicle> statusList = getCurrentStatus();
        return Response.ok(statusList).build();
    }


    @POST
    @Timed
    @UnitOfWork
    @Path("/update")
    public Response updateVehicleStatus(Vehicle vehicle) {
        VehicleDto existingVehicle = dao.findById(vehicle);
        if (existingVehicle != null) {
            existingVehicle.setStatus(vehicle.getStatus());
            dao.update(existingVehicle);
        } else {
            return Response.ok("{ \"status\": \"VEHICLE NOT FOUND IN SYSTEM\"}").build();
        }

        return Response.ok("{ \"status\": \"STATUS UPDATED\"}").build();
    }

    @GET
    @Timed
    @Path("/migrate")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response migrateExistingVehicles() {
        //TO DO
        // Meanwhile stubb some existing vehicles
        List<VehicleDto> currentVehicleAdded = StatusHelper.migrateSomeExistingVehiclesIntoSystem(vehicleDtos, dao, cDao, adDao, addressList, customers);
        if (vehicleDtos != null) {
            vehicleDtos.addAll(currentVehicleAdded);
        } else {
            vehicleDtos = currentVehicleAdded;
        }
        return Response.ok("{ \"status\": \"DATA MIGRATED\"}").build();
    }

    @GET
    @Timed
    @Path("/clear")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearExistingVehicles() {
        if (vehicleDtos != null) {
            StatusHelper.clearTestVehicles(vehicleDtos, dao);
            vehicleDtos = null;
            currentVehicleAdded = null;
            return Response.ok("{ \"status\": \"Test Data Deleted\"}").build();
        }
        return Response.ok("{ \"status\": \"Test Data already empty\"}").build();
    }

    private List<Vehicle> getCurrentStatus() {
        List<Vehicle> finalStatusList = new ArrayList<>();
        List<VehicleDto> vehicleStatus = dao.findAll();

        vehicleStatus.stream().forEach(v -> populateDomainVehicle(v, finalStatusList));

        return finalStatusList;
    }

    private void populateDomainVehicle(VehicleDto v, List<Vehicle> allVehicle) {
        Vehicle vehicle = new Vehicle(v.getVehicleCompositeId().getVehicleId(),
                v.getVehicleCompositeId().getRegistrationDate(),
                v.getStatus(), v.getRegNumber(), v.getOwnerId(), getCustomerName(customers, v.getOwnerId()),
                getAddrees(addressList, v.getOwnerId()));

        allVehicle.add(vehicle);
    }
}