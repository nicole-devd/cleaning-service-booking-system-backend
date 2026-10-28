
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.ServiceType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {

    @Query("SELECT st FROM ServiceType st WHERE st.cleaners IS EMPTY")
    List<ServiceType> findUnusedServiceTypes();
}
