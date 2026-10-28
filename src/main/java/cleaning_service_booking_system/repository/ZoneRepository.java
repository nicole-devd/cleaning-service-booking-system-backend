
package cleaning_service_booking_system.repository;

import cleaning_service_booking_system.model.Zone;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZoneRepository extends JpaRepository<Zone, UUID> {

    @Query("SELECT z FROM Zone z WHERE NOT EXISTS (SELECT c FROM Cleaner c WHERE c.zone = z) ORDER BY z.zoneNumber ASC")
    List<Zone> findFreeZones();
}
