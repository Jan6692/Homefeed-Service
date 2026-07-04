package com.br.inspo.Homefeed_Service.repository;

import com.br.inspo.Homefeed_Service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByLanguage(String language);

    List<User> findByLastName(String lastName);

}
