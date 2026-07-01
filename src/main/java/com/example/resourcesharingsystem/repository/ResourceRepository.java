package com.example.resourcesharingsystem.repository;

import com.example.resourcesharingsystem.entity.Resource;
import com.example.resourcesharingsystem.entity.ResourceCategory;
import com.example.resourcesharingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAll();

    List<Resource> findAllByOwner(User owner);
    Resource findById(Integer id);

    List<Resource> findAllByCategory(ResourceCategory category);
}
