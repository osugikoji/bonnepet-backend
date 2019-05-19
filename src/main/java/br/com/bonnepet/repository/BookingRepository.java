package br.com.bonnepet.repository;

import br.com.bonnepet.domain.Booking;
import br.com.bonnepet.domain.Host;
import br.com.bonnepet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findBookingsByHostAndUser(Host host, User user);

}
