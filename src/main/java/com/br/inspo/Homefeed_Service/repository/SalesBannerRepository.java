package com.br.inspo.Homefeed_Service.repository;

import com.br.inspo.Homefeed_Service.entity.SalesBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesBannerRepository extends JpaRepository<SalesBanner, Long> {

    List<SalesBanner> findByLanguage(String language);
}
