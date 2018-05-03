package statusAppl.domain;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Jai on 4/28/2018.
 */
@Data
@Builder
public class Address {
    String streetName;
    String streetNum;
    String post;
    String city;
    String country;
}
