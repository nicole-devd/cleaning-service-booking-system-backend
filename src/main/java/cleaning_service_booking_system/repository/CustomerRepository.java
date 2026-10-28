
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT b.customer FROM Booking b WHERE b.cleaner.id = :cleanerId")
    List<Customer> findCustomersOfCleaner(@Param("cleanerId") UUID cleanerId);
}
