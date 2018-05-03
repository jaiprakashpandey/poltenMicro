package statusAppl.restapi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import statusAppl.db.VehicleDao;
import statusAppl.db.entity.VehicleDto;
import statusAppl.db.entity.VehiclePK;
import statusAppl.domain.Status;
import statusAppl.domain.Vehicle;
import statusAppl.util.StatusHelper;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doNothing;

/**
 * Created by K316940 on 5/2/2018.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(StatusHelper.class)
public class VehiclesMicroServiceResourceTest {
    private VehiclesMicroServiceResource resource;


    private VehicleDao vDao;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(StatusHelper.class);
        vDao = PowerMockito.mock(VehicleDao.class);
        resource = new VehiclesMicroServiceResource(vDao, null, null);
    }

    @Test
    public void testGetVehicleStatus() throws Exception {
        List<VehicleDto> mockedVehicles = getVehicles();
        when(vDao.findAll()).thenReturn(mockedVehicles);
        when(StatusHelper.getCustomerName(any(), eq("OWN-123"))).thenReturn("Mr Bond");
        when(StatusHelper.getAddrees(any(), eq("OWN-123"))).thenReturn("Bond st 007, Stockholm SE");

        Response response = resource.getVehicleStatus();

        List<Vehicle> respVehicles = (List<Vehicle>) response.getEntity();
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        Assert.assertNotNull(respVehicles.get(0));
        Assert.assertEquals("CON", respVehicles.get(0).getStatus());
    }

    @Test
    public void testUpdateVehicleStatusIfVehicleExistInSystem() throws Exception {
        VehicleDto mockedVehicle = getVehicles().get(0);
        when(vDao.findById(any())).thenReturn(mockedVehicle);
        doNothing().when(vDao).update(mockedVehicle);
        Vehicle vehicleToUpdate = createAvehicle();

        Response response = resource.updateVehicleStatus(vehicleToUpdate);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals("{ \"status\": \"STATUS UPDATED\"}", response.getEntity().toString());

    }

    @Test
    public void testUpdateVehicleStatusIfVehicleDoesNotExistInSystem() throws Exception {
        VehicleDto mockedVehicle = getVehicles().get(0);
        when(vDao.findById(any())).thenReturn(null);
        doNothing().when(vDao).update(mockedVehicle);
        Vehicle vehicleToUpdate = createAvehicle();

        Response response = resource.updateVehicleStatus(vehicleToUpdate);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals("{ \"status\": \"VEHICLE NOT FOUND IN SYSTEM\"}", response.getEntity().toString());

    }

    @Test
    public void testMigrateExistingVehicles() throws Exception {
        List<VehicleDto> mockedVehicles = getVehicles();
        when(StatusHelper.migrateSomeExistingVehiclesIntoSystem(any(), any(), any(), any(), any(), any())).thenReturn(mockedVehicles);

        Response response = resource.migrateExistingVehicles();
        String serviceResp = (String) response.getEntity();

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals("{ \"status\": \"DATA MIGRATED\"}", serviceResp);
    }

    @Test
    public void testClearExistingVehicles() throws Exception {
        List<VehicleDto> mockedVehicles = getVehicles();
        resource.setVehicleDtos(mockedVehicles);
        doNothing().when(vDao).delete(any());

        Response response = resource.clearExistingVehicles();

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals("{ \"status\": \"Test Data Deleted\"}", response.getEntity().toString());
    }

    @Test
    public void testClearExistingTestIfAlreadyDeleted() throws Exception {
        doNothing().when(vDao).delete(any());

        Response response = resource.clearExistingVehicles();

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals("{ \"status\": \"Test Data already empty\"}", response.getEntity().toString());
    }


    private List<VehicleDto> getVehicles() {
        List<VehicleDto> vehicles = new ArrayList<>();
        VehicleDto v1 = VehicleDto.builder().regNumber("XXX007").ownerId("OWN-123").status(Status.CONNECTED.getStatusCode()).build();
        VehiclePK pk = new VehiclePK("VIN00000000000017", new Date());
        v1.setVehicleCompositeId(pk);
        vehicles.add(v1);
        return vehicles;
    }

    private Vehicle createAvehicle() {
        return new Vehicle("VIN00000000000017", new Date(), "CON", "XYZ000", "OWN-123", "Jai", "Bond st 007, Stockholm SE");
    }
}