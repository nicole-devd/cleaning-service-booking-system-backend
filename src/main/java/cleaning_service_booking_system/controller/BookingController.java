
package cleaning_service_booking_system.controller;

import cleaning_service_booking_system.model.Booking;
import cleaning_service_booking_system.model.BookingStatus;
import cleaning_service_booking_system.service.BookingService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Booking booking) {
        String result = bookingService.save(booking);

        if (result.equals("Booking date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else if (result.equals("Cleaner already has a booking on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Booking>> findByStatus(
            @RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<Booking>> findBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return ResponseEntity.ok(
                bookingService.findBetweenDates(startDate, endDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Booking> booking = bookingService.findById(id);

        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The booking with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable UUID id,
            @RequestBody Booking booking) {

        String result = bookingService.update(id, booking);

        if (result.equals("The booking with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(
            @PathVariable UUID id,
            @RequestParam BookingStatus status) {

        String result = bookingService.changeStatus(id, status);

        if (result.equals("The booking with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.startsWith("Cannot change status from")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String result = bookingService.delete(id);

        if (result.equals("The booking with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
