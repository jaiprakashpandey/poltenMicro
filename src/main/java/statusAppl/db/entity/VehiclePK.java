package statusAppl.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by k316940 on 4/30/2018.
 */
@Data
@Embeddable
public class VehiclePK implements Serializable {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "vehicleId")
    private String vehicleId;

    @Column(name = "regDate")
    private Date registrationDate;

    public VehiclePK() {
    }

    public VehiclePK(String vehicleId, Date registrationDate) {
        this.vehicleId = vehicleId;
        this.registrationDate = registrationDate;
    }
}
