package com.buysellcars.repositories;

import com.buysellcars.entities.User;
import com.buysellcars.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findByUserRole(UserRole userRole);
}
