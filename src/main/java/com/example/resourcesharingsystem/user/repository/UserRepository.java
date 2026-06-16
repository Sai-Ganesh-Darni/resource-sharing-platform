package com.example.resourcesharingsystem.user.repository;

import com.example.resourcesharingsystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    User findByEmail(String email);

}
