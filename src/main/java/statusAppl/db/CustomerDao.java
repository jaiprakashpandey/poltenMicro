package statusAppl.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import statusAppl.db.entity.CustomerDto;

import java.util.List;

/**
 * Created by k316940 on 4/30/2018.
 */
public class CustomerDao extends AbstractDAO<CustomerDto> {
    public CustomerDao(SessionFactory factory) {
        super(factory);
    }

    public void update(CustomerDto CustomerDto) {
        this.currentSession().update(CustomerDto.class.getName(), CustomerDto);
    }

    public void create(List<CustomerDto> cs) {
        Session ssn = this.currentSession();
        ssn.flush();
        cs.forEach(c -> {
            persist(c);
        });
        ssn.clear();
    }

    public void delete(CustomerDto CustomerDto) {
        this.currentSession().delete(CustomerDto);
    }

    public List<CustomerDto> findAll() {
        return currentSession().createCriteria(CustomerDto.class).list();
    }
}
