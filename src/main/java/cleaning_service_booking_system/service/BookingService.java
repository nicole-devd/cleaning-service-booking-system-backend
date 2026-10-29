
package cleaning_service_booking_system.service;

import cleaning_service_booking_system.model.Booking;
import cleaning_service_booking_system.model.BookingStatus;
import cleaning_service_booking_system.repository.BookingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String save(Booking booking) {
        if (booking.getServiceDate().isBefore(LocalDate.now())) {
            return "Booking date cannot be in the past";
        }

        if (booking.getCleaner() != null
                && bookingRepository.existsByCleanerAndServiceDateAndStatusNot(
                        booking.getCleaner(),
                        booking.getServiceDate(),
                        BookingStatus.CANCELLED)) {
            return "Cleaner already has a booking on that date";
        }

        booking.setStatus(BookingStatus.REQUESTED);
        bookingRepository.save(booking);
        return "Booking saved successfully";
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(UUID id) {
        return bookingRepository.findById(id);
    }

    public String update(UUID id, Booking booking) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);

        if (existingBooking.isEmpty()) {
            return "The booking with that id does not exist";
        }

        Booking currentBooking = existingBooking.get();
        currentBooking.setServiceDate(booking.getServiceDate());
        currentBooking.setAddress(booking.getAddress());
        currentBooking.setRooms(booking.getRooms());
        currentBooking.setCustomer(booking.getCustomer());
        currentBooking.setCleaner(booking.getCleaner());

        bookingRepository.save(currentBooking);
        return "Booking updated successfully";
    }

    public String changeStatus(UUID id, BookingStatus newStatus) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);

        if (existingBooking.isEmpty()) {
            return "The booking with that id does not exist";
        }

        Booking booking = existingBooking.get();
        BookingStatus currentStatus = booking.getStatus();

        boolean allowed = false;

        if (currentStatus == BookingStatus.REQUESTED
                && (newStatus == BookingStatus.ASSIGNED
                || newStatus == BookingStatus.CANCELLED)) {
            allowed = true;
        } else if (currentStatus == BookingStatus.ASSIGNED
                && (newStatus == BookingStatus.CLEANED
                || newStatus == BookingStatus.CANCELLED)) {
            allowed = true;
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        booking.setStatus(newStatus);
        bookingRepository.save(booking);
        return "Booking status updated successfully";
    }

    public String delete(UUID id) {
        if (!bookingRepository.existsById(id)) {
            return "The booking with that id does not exist";
        }

        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }

    public List<Booking> findByStatus(BookingStatus status) {
        return bookingRepository.findByStatusOrderByServiceDateAsc(status);
    }

    public List<Booking> findBetweenDates(LocalDate start, LocalDate end) {
        return bookingRepository.findByServiceDateBetweenOrderByServiceDateAsc(
                start, end);
    }
}
