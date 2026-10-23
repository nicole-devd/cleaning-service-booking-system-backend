
package cleaning_service_booking_system.controller;

import cleaning_service_booking_system.model.Zone;
import cleaning_service_booking_system.service.ZoneService;
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
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Zone zone) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(zoneService.save(zone));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Zone>> findAll() {
        return ResponseEntity.ok(zoneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Zone> zone = zoneService.findById(id);

        if (zone.isPresent()) {
            return ResponseEntity.ok(zone.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The zone with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Zone zone) {

        String result = zoneService.update(id, zone);

        if (result.equals("The zone with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = zoneService.delete(id);

        if (result.equals("The zone with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
