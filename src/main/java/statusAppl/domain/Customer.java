package statusAppl.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Jai on 4/28/2018.
 */
@Data
@Builder
public class Customer {
    List<Vehicle> vehicles;
    String custId;
    Address address;
}
