package statusAppl.util;

import statusAppl.db.AddressDao;
import statusAppl.db.CustomerDao;
import statusAppl.db.VehicleDao;
import statusAppl.db.entity.AddressDto;
import statusAppl.db.entity.CustomerDto;
import statusAppl.db.entity.VehicleDto;
import statusAppl.db.entity.VehiclePK;
import statusAppl.domain.Status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by Jai on 4/29/2018.
 */
public class StatusHelper {

    public static List<VehicleDto> migrateSomeExistingVehiclesIntoSystem(List<VehicleDto> vehicleDtos, VehicleDao dao, CustomerDao cDao, AddressDao aDao, List<AddressDto> addresses, List<CustomerDto> customers) {

        if (customers.isEmpty())
            registerCustomers(customers, cDao);
        if (addresses.isEmpty())
            registerCustomersAddress(addresses, aDao);

        List<VehicleDto> vehicleDtosFresh = getVehicleDtos(vehicleDtos);
        vehicleDtosFresh.stream().forEach(v -> {
            dao.create(v);
        });

        return vehicleDtosFresh;
    }

    private static void registerCustomers(List<CustomerDto> customers, CustomerDao cDao) {
        CustomerDto c1 = CustomerDto.builder().customerId("K123-83").name("Kalles Grustransporter AB").build();
        CustomerDto c2 = CustomerDto.builder().customerId("JO456-92").name("Johans Bulk AB").build();
        CustomerDto c3 = CustomerDto.builder().customerId("HA007-76").name("Haralds Värdetransporter AB").build();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        cDao.create(customers);
    }

    private static void registerCustomersAddress(List<AddressDto> addresses, AddressDao aDao) {
        AddressDto a1 = AddressDto.builder().streetName("Balkvägen").streetNum("12").custId("JO456-92").post("222 22").city("Stockholm").country("SE").build();
        AddressDto a2 = AddressDto.builder().streetName("Cementvägen").streetNum("8").custId("K123-83").post("111 11").city("Södertälje").country("SE").build();
        AddressDto a3 = AddressDto.builder().streetName("Budgetvägen").streetNum("1").custId("HA007-76").post("333 33").city("Uppsala").country("SE").build();
        addresses.add(a1);
        addresses.add(a2);
        addresses.add(a3);

        aDao.create(addresses);
    }

    private static List<VehicleDto> getVehicleDtos(List<VehicleDto> oldVehicles) {
        List<VehicleDto> vehicleDtos = new ArrayList<>();
        if (oldVehicles != null && !oldVehicles.isEmpty()) {
            oldVehicles.stream().forEach(v -> {
                Random random = new Random();
                String newRegistration = String.format("%04d", random.nextInt(1000));
                VehicleDto freshArrivals = VehicleDto.builder().vehicleCompositeId(manufactureNewVehicles(v, random)).regNumber("XXX" + newRegistration).ownerId("K123-83").status(Status.CONNECTED.getStatusCode()).build();
                vehicleDtos.add(freshArrivals);
            });
            return vehicleDtos;
        } else {
            VehicleDto v1 = VehicleDto.builder().vehicleCompositeId(composeVehicle("YS2R4X20005399401", LocalDate.now().minusYears(3).plusDays(5))).regNumber("ABC123").ownerId("K123-83").status(Status.CONNECTED.getStatusCode()).build();
            VehicleDto v2 = VehicleDto.builder().vehicleCompositeId(composeVehicle("VLUR4X20009093588", LocalDate.now().minusYears(5).plusDays(10))).regNumber("DEF456").ownerId("K123-83").status(Status.CONNECTED.getStatusCode()).build();
            VehicleDto v3 = VehicleDto.builder().vehicleCompositeId(composeVehicle("VLUR4X20009048066", LocalDate.now().minusYears(15).plusDays(10))).regNumber("GHI789").ownerId("K123-83").status(Status.CONNECTED.getStatusCode()).build();

            VehicleDto v4 = VehicleDto.builder().vehicleCompositeId(composeVehicle("YS2R4X20005388011", LocalDate.now().minusYears(1).plusDays(10))).regNumber("JKL123").ownerId("JO456-92").status(Status.CONNECTED.getStatusCode()).build();
            VehicleDto v5 = VehicleDto.builder().vehicleCompositeId(composeVehicle("YS2R4X20005387949", LocalDate.now().minusYears(2).plusDays(10))).regNumber("MNOP123").ownerId("JO456-92").status(Status.CONNECTED.getStatusCode()).build();

            VehicleDto v6 = VehicleDto.builder().vehicleCompositeId(composeVehicle("VLUR4X20009048066", LocalDate.now().minusYears(20).plusDays(10))).regNumber("PQR678").ownerId("HA007-76").status(Status.DISCONNECTED.getStatusCode()).build();
            VehicleDto v7 = VehicleDto.builder().vehicleCompositeId(composeVehicle("YS2R4X20005387055", LocalDate.now().minusYears(20).plusDays(10))).regNumber("STU901").ownerId("HA007-76").status(Status.CONNECTED.getStatusCode()).build();

            vehicleDtos.add(v1);
            vehicleDtos.add(v2);
            vehicleDtos.add(v3);
            vehicleDtos.add(v4);
            vehicleDtos.add(v5);
            vehicleDtos.add(v6);
            vehicleDtos.add(v7);
        }

        return vehicleDtos;
    }

    public static VehiclePK manufactureNewVehicles(VehicleDto v, Random random) {
        String id = String.format("%04d", random.nextInt(10000));
        String oldVin = v.getVehicleCompositeId().getVehicleId();
        String newVin = oldVin.substring(0, oldVin.length() - 4) + id;
        return composeVehicle(newVin, LocalDate.now().minusYears(2).plusDays(random.nextInt(1000)));
    }

    public static VehiclePK composeVehicle(String id, LocalDate registrationNum) {
        return new VehiclePK(id, Date.from(registrationNum.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }


    public static void clearTestVehicles(List<VehicleDto> vehicles, VehicleDao dao) {
        vehicles.stream().forEach(v -> {
            dao.delete(v);
        });
    }

    public static String getAddrees(List<AddressDto> addressList, String ownerId) {
        Optional<AddressDto> addressDto = addressList.stream().filter(a -> a.getCustId().equalsIgnoreCase(ownerId)).findAny();
        return addressDto.map(AddressDto::toString).orElse("Address not registered");
    }

    public static String getCustomerName(List<CustomerDto> customerList, String id) {
        Optional<CustomerDto> cust = customerList.stream().filter(customerDto -> customerDto.getCustomerId().equalsIgnoreCase(id)).findAny();
        return cust.map(customerDto -> customerDto.getName()).orElse("Name not registered");
    }
}