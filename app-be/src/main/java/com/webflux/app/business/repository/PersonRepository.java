package com.webflux.app.business.repository;

import com.webflux.app.business.domain.Person;
import org.springframework.data.repository.CrudRepository;


public interface PersonRepository extends CrudRepository<Person, Long> {
  Person findFirstByEmail(String email);


//  @Query("Update Person SET nickname = :nickname, email = :email where id = :id")
//  Person updatePerson(@Param("id") Long id, @Param("nickname") String nickname, @Param("email") String email);
}
