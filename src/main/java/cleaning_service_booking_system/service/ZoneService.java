
package cleaning_service_booking_system.service;

import cleaning_service_booking_system.model.Zone;
import cleaning_service_booking_system.repository.ZoneRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public String save(Zone zone) {
        zoneRepository.save(zone);
        return "Zone saved successfully";
    }

    public List<Zone> findAll() {
        return zoneRepository.findAll();
    }

    public Optional<Zone> findById(UUID id) {
        return zoneRepository.findById(id);
    }

    public String update(UUID id, Zone zone) {
        Optional<Zone> existingZone = zoneRepository.findById(id);

        if (existingZone.isEmpty()) {
            return "The zone with that id does not exist";
        }

        Zone currentZone = existingZone.get();
        currentZone.setZoneNumber(zone.getZoneNumber());
        currentZone.setName(zone.getName());

        zoneRepository.save(currentZone);
        return "Zone updated successfully";
    }

    public String delete(UUID id) {
        if (!zoneRepository.existsById(id)) {
            return "The zone with that id does not exist";
        }

        zoneRepository.deleteById(id);
        return "Zone deleted successfully";
    }
}
