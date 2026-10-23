
package cleaning_service_booking_system.service;

import cleaning_service_booking_system.model.Cleaner;
import cleaning_service_booking_system.repository.CleanerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CleanerService {

    private final CleanerRepository cleanerRepository;

    public CleanerService(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
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

    public String delete(UUID id) {
        if (!cleanerRepository.existsById(id)) {
            return "The cleaner with that id does not exist";
        }

        cleanerRepository.deleteById(id);
        return "Cleaner deleted successfully";
    }
}
