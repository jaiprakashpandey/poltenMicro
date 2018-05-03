package statusAppl.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import statusAppl.db.entity.AddressDto;

import java.util.List;

/**
 * Created by k316940 on 4/30/2018.
 */
public class AddressDao extends AbstractDAO<AddressDto> {
    public AddressDao(SessionFactory factory) {
        super(factory);
    }

    public void update(AddressDto addressDto) {
        this.currentSession().update(AddressDto.class.getName(), addressDto);
    }

    public void create(List<AddressDto> adrs) {
        Session ssn = this.currentSession();
        ssn.clear();
        Session ssn2 = this.currentSession().getSessionFactory().openSession();
        adrs.forEach(a -> {
            ssn2.save(a);
        });
    }

    public void delete(AddressDto addressDto) {
        this.currentSession().delete(addressDto);
    }

    public List<AddressDto> findAll() {
        return currentSession().createCriteria(AddressDto.class).list();
    }
}
