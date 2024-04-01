package com.shanks.springbootsecurityjwt.repository;

import com.shanks.springbootsecurityjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    List<User> deleteByEmail(String email);

    User findByEmail(String email);
    Optional<User> findByName(String name);

}
