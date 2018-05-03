package statusAppl.db.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Jai on 4/29/2018.
 */
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "address")
@NamedQueries(@NamedQuery(name = "statusAppl.db.entity.AddressDto.findAll", query = "select a from AddressDto a"))
public class AddressDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "custId", nullable = false)
    private String custId;

    @Column(name = "streetName", nullable = false)
    private String streetName;
    @Column(name = "streetNum", nullable = false)
    private String streetNum;
    @Column(name = "post", nullable = false)
    private String post;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false)
    private String country;

    @Override
    public String toString() {
        return streetName + " " + streetNum + "," + post + " " + city + " " + country;
    }

    public AddressDto() {
    }
}