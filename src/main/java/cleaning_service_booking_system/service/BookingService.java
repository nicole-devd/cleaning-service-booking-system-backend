
package cleaning_service_booking_system.service;

import cleaning_service_booking_system.model.Booking;
import cleaning_service_booking_system.repository.BookingRepository;
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

    public String delete(UUID id) {
        if (!bookingRepository.existsById(id)) {
            return "The booking with that id does not exist";
        }

        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }
}
