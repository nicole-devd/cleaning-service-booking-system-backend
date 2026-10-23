
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Cleaner;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CleanerRepository extends JpaRepository<Cleaner, UUID> {
}
