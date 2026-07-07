package com.br.inspo.Homefeed_Service.repository;

import com.br.inspo.Homefeed_Service.entity.SaleBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for access of SaleBanner database.
 */
@Repository
public interface SaleBannerRepository extends JpaRepository<SaleBanner, Long> {

    List<SaleBanner> findByLanguage(String language);
}
