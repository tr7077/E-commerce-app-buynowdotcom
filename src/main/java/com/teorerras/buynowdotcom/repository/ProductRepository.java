package com.teorerras.buynowdotcom.repository;

import com.teorerras.buynowdotcom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findByCategory(String category);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByBrand(String brand);

    @Query("SELECT p FROM PRODUCT p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(String name);

    boolean existsByNameAndBrand(String name, String brand);
}
