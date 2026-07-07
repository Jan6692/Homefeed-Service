package com.br.inspo.Homefeed_Service.repository;

import com.br.inspo.Homefeed_Service.entity.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for access of Greeting database.
 */
@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {

    List<Greeting> findByLanguage(String language);
}
