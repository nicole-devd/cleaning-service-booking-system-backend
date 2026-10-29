
package cleaning_service_booking_system.service;

import cleaning_service_booking_system.model.Cleaner;
import cleaning_service_booking_system.model.ServiceType;
import cleaning_service_booking_system.model.Zone;
import cleaning_service_booking_system.repository.CleanerRepository;
import cleaning_service_booking_system.repository.ServiceTypeRepository;
import cleaning_service_booking_system.repository.ZoneRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CleanerService {

    private final CleanerRepository cleanerRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ZoneRepository zoneRepository;

    public CleanerService(
            CleanerRepository cleanerRepository,
            ServiceTypeRepository serviceTypeRepository,
            ZoneRepository zoneRepository) {
        this.cleanerRepository = cleanerRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.zoneRepository = zoneRepository;
    }

    public String save(Cleaner cleaner) {
        cleanerRepository.save(cleaner);
        return "Cleaner saved successfully";
    }

    public List<Cleaner> findAll() {
        return cleanerRepository.findAll();
    }

    public Optional<Cleaner> findById(UUID id) {
        return cleanerRepository.findById(id);
    }

    public String update(UUID id, Cleaner cleaner) {
        Optional<Cleaner> existingCleaner = cleanerRepository.findById(id);

        if (existingCleaner.isEmpty()) {
            return "The cleaner with that id does not exist";
        }

        Cleaner currentCleaner = existingCleaner.get();
        currentCleaner.setFirstName(cleaner.getFirstName());
        currentCleaner.setLastName(cleaner.getLastName());
        currentCleaner.setPhone(cleaner.getPhone());
        currentCleaner.setZone(cleaner.getZone());
        currentCleaner.setServiceTypes(cleaner.getServiceTypes());

        cleanerRepository.save(currentCleaner);
        return "Cleaner updated successfully";
    }

    public String addServiceType(UUID cleanerId, UUID serviceTypeId) {
        Optional<Cleaner> existingCleaner = cleanerRepository.findById(cleanerId);

        if (existingCleaner.isEmpty()) {
            return "The cleaner with that id does not exist";
        }

        Optional<ServiceType> existingServiceType =
                serviceTypeRepository.findById(serviceTypeId);

        if (existingServiceType.isEmpty()) {
            return "That service type does not exist";
        }

        Cleaner cleaner = existingCleaner.get();
        ServiceType serviceType = existingServiceType.get();

        if (cleaner.getServiceTypes() == null) {
            cleaner.setServiceTypes(new ArrayList<>());
        }

        for (ServiceType existing : cleaner.getServiceTypes()) {
            if (existing.getId().equals(serviceType.getId())) {
                return "The cleaner already has that service type";
            }
        }

        cleaner.getServiceTypes().add(serviceType);
        cleanerRepository.save(cleaner);

        return "Service type added to the cleaner";
    }

    public String changeZone(UUID cleanerId, int zoneNumber) {
        Optional<Cleaner> existingCleaner = cleanerRepository.findById(cleanerId);

        if (existingCleaner.isEmpty()) {
            return "The cleaner with that id does not exist";
        }

        Optional<Zone> existingZone = zoneRepository.findByZoneNumber(zoneNumber);

        if (existingZone.isEmpty()) {
            return "The zone with that zoneNumber does not exist";
        }

        Cleaner cleaner = existingCleaner.get();
        Zone zone = existingZone.get();

        if (cleaner.getZone() != null
                && cleaner.getZone().getId().equals(zone.getId())) {
            return "Zone changed successfully";
        }

        if (cleanerRepository.existsByZone(zone)) {
            return "That zone is taken by another cleaner";
        }

        cleaner.setZone(zone);
        cleanerRepository.save(cleaner);

        return "Zone changed successfully";
    }

    public String delete(UUID id) {
        if (!cleanerRepository.existsById(id)) {
            return "The cleaner with that id does not exist";
        }

        cleanerRepository.deleteById(id);
        return "Cleaner deleted successfully";
    }

    public List<Cleaner> findByServiceTypeName(String name) {
        return cleanerRepository.findByServiceTypeName(name);
    }
}
