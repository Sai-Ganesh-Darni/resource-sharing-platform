package com.example.resourcesharingsystem.resource.repository;

import com.example.resourcesharingsystem.resource.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
