
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Booking;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
