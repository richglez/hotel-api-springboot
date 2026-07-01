package com.richglez.hotel.users.repository;

import com.richglez.hotel.users.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data JPA repository for {@link User} entities. */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return the matching user, if any
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds all users that have not been soft-deleted.
     *
     * @return the list of active users
     */
    List<User> findByDeletedAtIsNull();

    /**
     * Finds all users that have been soft-deleted.
     *
     * @return the list of soft-deleted users
     */
    List<User> findByDeletedAtIsNotNull();
}
