package com.example.resourcesharingsystem.user.repository;

import com.example.resourcesharingsystem.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    public List<UserAddress> findByUserId(Long userId);
}
