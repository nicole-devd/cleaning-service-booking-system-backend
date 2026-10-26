
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Booking;
import cleaning_service_booking_system.model.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByStatusOrderByServiceDateAsc(BookingStatus status);

    List<Booking> findByServiceDateBetweenOrderByServiceDateAsc(
            LocalDate start, LocalDate end);
}
