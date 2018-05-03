package statusAppl.db.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by k316940 on 4/30/2018.
 */
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "customer")
@NamedQueries(@NamedQuery(name = "statusAppl.db.entity.CustomerDto.findAll", query = "select a from CustomerDto a"))
public class CustomerDto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "customerId", nullable = false)
    private String customerId;

    public CustomerDto() {
    }
}
