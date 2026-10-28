
package cleaning_service_booking_system.controller;

import cleaning_service_booking_system.model.Cleaner;
import cleaning_service_booking_system.service.CleanerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cleaners")
public class CleanerController {

    private final CleanerService cleanerService;

    public CleanerController(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Cleaner cleaner) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cleanerService.save(cleaner));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cleaner>> findAll() {
        return ResponseEntity.ok(cleanerService.findAll());
    }

    @GetMapping("/by-service-type")
    public ResponseEntity<List<Cleaner>> findByServiceTypeName(
            @RequestParam String name) {
        return ResponseEntity.ok(cleanerService.findByServiceTypeName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Cleaner> cleaner = cleanerService.findById(id);

        if (cleaner.isPresent()) {
            return ResponseEntity.ok(cleaner.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The cleaner with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Cleaner cleaner) {

        String result = cleanerService.update(id, cleaner);

        if (result.equals("The cleaner with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = cleanerService.delete(id);

        if (result.equals("The cleaner with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
