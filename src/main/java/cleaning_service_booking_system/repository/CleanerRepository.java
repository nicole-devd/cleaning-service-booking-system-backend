
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Cleaner;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CleanerRepository extends JpaRepository<Cleaner, UUID> {

    @Query("SELECT DISTINCT c FROM Cleaner c JOIN c.serviceTypes st WHERE LOWER(st.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Cleaner> findByServiceTypeName(@Param("name") String name);
}
