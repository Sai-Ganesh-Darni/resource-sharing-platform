package com.example.resourcesharingsystem.repository;

import com.example.resourcesharingsystem.entity.Booking;
import com.example.resourcesharingsystem.entity.Resource;
import com.example.resourcesharingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByResource(Resource resource);

    List<Booking> findAllByRequestedBy(User requestedBy);
}
