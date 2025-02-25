package com.dearcoding.recommendationsystem.repository;

import com.dearcoding.recommendationsystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
