package com.example.resourcesharingsystem.repository;

import com.example.resourcesharingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedFalse();

    User findById(long id);
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);


}
