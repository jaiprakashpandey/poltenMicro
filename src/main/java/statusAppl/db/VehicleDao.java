package statusAppl.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import statusAppl.db.entity.VehicleDto;
import statusAppl.domain.Vehicle;

import java.util.List;

/**
 * Created by Jai on 4/29/2018.
 */
public class VehicleDao extends AbstractDAO<VehicleDto> {
    public VehicleDao(SessionFactory factory) {
        super(factory);
    }

    public VehicleDto findById(Vehicle vehicle) {
        Criteria cr = this.currentSession().createCriteria(VehicleDto.class);
        Criterion cron = Restrictions.conjunction().add(Restrictions.eq("id.vehicleId", vehicle.getVehicleId())).add(Restrictions.eq("id.registrationDate", vehicle.getRegistrationDate()));
        List<VehicleDto> results = cr.add(cron).list();
        if (!results.isEmpty())
            return results.get(0);
        return null;
    }

    public VehicleDto create(VehicleDto vehicleDto) {
        return persist(vehicleDto);
    }


    public void update(VehicleDto vehicleDto) {
        this.currentSession().update(VehicleDto.class.getName(), vehicleDto);
    }

    public void delete(VehicleDto vehicleDto) {
        this.currentSession().delete(vehicleDto);
    }

    public List<VehicleDto> findAll() {
        return currentSession().createCriteria(VehicleDto.class).list();
    }
}