
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.ServiceType;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {
}
