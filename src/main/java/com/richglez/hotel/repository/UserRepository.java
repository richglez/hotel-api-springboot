package com.richglez.hotel.repository;

import com.richglez.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // SELECT * FROM user WHERE deleted_at IS NULL
    List<User> findByDeletedAtIsNull();

    // SELECT * FROM user WHERE deleted_at IS NOT NULL
    List<User> findByDeletedAtIsNotNull();
}
