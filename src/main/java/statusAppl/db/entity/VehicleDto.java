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
@Table(name = "vehicle")
@NamedQueries(@NamedQuery(name = "statusAppl.db.entity.VehicleDto.findAll", query = "select v from VehicleDto v"))
public class VehicleDto implements Serializable {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private VehiclePK vehicleCompositeId;

    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "regNumber", nullable = false)
    private String regNumber;
    @Column(name = "ownerId", nullable = false)
    private String ownerId;

    public VehicleDto() {
    }
}