package com.example.resourcesharingsystem.repository;
import com.example.resourcesharingsystem.entity.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, Long> {
    ResourceCategory findByName(String name);
    boolean existsByName(String name);
    ResourceCategory findById(int id);
    List<ResourceCategory> findAllByOrderByNameAsc();
}
