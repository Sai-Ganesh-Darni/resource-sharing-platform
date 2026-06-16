package com.example.resourcesharingsystem.resource.repository;
import com.example.resourcesharingsystem.resource.entity.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, Long> {
    ResourceCategory findByName(String name);
    boolean existsByName(String name);
    ResourceCategory findById(int id);
}
