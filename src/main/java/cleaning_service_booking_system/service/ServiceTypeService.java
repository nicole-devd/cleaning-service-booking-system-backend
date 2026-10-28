
package cleaning_service_booking_system.service;

import cleaning_service_booking_system.model.ServiceType;
import cleaning_service_booking_system.repository.ServiceTypeRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public String save(ServiceType serviceType) {
        serviceTypeRepository.save(serviceType);
        return "Service type saved successfully";
    }

    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    public Optional<ServiceType> findById(UUID id) {
        return serviceTypeRepository.findById(id);
    }

    public String update(UUID id, ServiceType serviceType) {
        Optional<ServiceType> existingServiceType =
                serviceTypeRepository.findById(id);

        if (existingServiceType.isEmpty()) {
            return "The service type with that id does not exist";
        }

        ServiceType currentServiceType = existingServiceType.get();
        currentServiceType.setName(serviceType.getName());

        serviceTypeRepository.save(currentServiceType);
        return "Service type updated successfully";
    }

    public String delete(UUID id) {
        if (!serviceTypeRepository.existsById(id)) {
            return "The service type with that id does not exist";
        }

        serviceTypeRepository.deleteById(id);
        return "Service type deleted successfully";
    }

    public List<ServiceType> findUnusedServiceTypes() {
        return serviceTypeRepository.findUnusedServiceTypes();
    }
}
