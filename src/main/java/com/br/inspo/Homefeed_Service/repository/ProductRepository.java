package com.br.inspo.Homefeed_Service.repository;

import com.br.inspo.Homefeed_Service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for access of Product database.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByPriceLessThanEqual(double price); // sample custom query
}
