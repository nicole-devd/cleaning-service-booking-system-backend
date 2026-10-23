
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Zone;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {
}
