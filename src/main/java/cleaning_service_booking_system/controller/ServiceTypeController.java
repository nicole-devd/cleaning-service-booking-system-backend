
package cleaning_service_booking_system.controller;

import cleaning_service_booking_system.model.ServiceType;
import cleaning_service_booking_system.service.ServiceTypeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ServiceType serviceType) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceTypeService.save(serviceType));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceType>> findAll() {
        return ResponseEntity.ok(serviceTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<ServiceType> serviceType = serviceTypeService.findById(id);

        if (serviceType.isPresent()) {
            return ResponseEntity.ok(serviceType.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The service type with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody ServiceType serviceType) {

        String result = serviceTypeService.update(id, serviceType);

        if (result.equals("The service type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = serviceTypeService.delete(id);

        if (result.equals("The service type with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
