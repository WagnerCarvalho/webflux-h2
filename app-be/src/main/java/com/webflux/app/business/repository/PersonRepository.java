package com.webflux.app.business.repository;

import com.webflux.app.business.domain.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends CrudRepository<Person, Long> {
  Person findFirstByEmail(String email);


  @Override
  default boolean existsById(Long aLong) {
    return false;
  }

    @Query("Update Person SET nickname = :nickname, email = :email where id = :id")
  Person updatePerson(@Param("id") Long id, @Param("nickname") String nickname, @Param("email") String email);
}
