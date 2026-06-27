package com.richglez.hotel.users.service;

import com.richglez.hotel.users.dto.UserPatchRequest;
import com.richglez.hotel.users.dto.UserPutRequest;
import com.richglez.hotel.users.dto.UserResponse;
import com.richglez.hotel.users.model.User;
import com.richglez.hotel.users.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



/**
 * Service layer for user management: retrieval, updates, and deletion.
 * Works with {@link User} entities internally and exposes {@link UserResponse} DTOs externally.
 */
@Service
@RequiredArgsConstructor
// Inyeccion de dependencias Lombok generará el constructor para userRepository y passwordEncoder
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ── Métodos públicos (devuelven DTO) ─────────────────────────

    /**
     * Retrieves all users that have not been soft-deleted.
     *
     * @return the list of active users, mapped to their public representation
     */
    public List<UserResponse> findAll() {
        return userRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Retrieves a single user by its ID.
     *
     * @param id the user's ID
     * @return the user, mapped to its public representation
     * @throws RuntimeException if no user exists with the given id
     */
    public UserResponse getById(Long id) {
        return toResponse(findById(id));
    }

    /**
     * Replaces every field of the user identified by {@code id} with the values from
     * {@code userPutRequest} (full replacement, as expected from a PUT request).
     *
     * @param id             the ID of the user to update
     * @param userPutRequest the new values for all user fields
     * @return the updated user, mapped to its public representation
     * @throws RuntimeException if no user exists with the given id
     */
    public UserResponse update(Long id, UserPutRequest userPutRequest) {
        User user = findById(id);

        user.setName(userPutRequest.getName());
        user.setLastName(userPutRequest.getLastName());
        user.setEmail(userPutRequest.getEmail());

        String securedPassword = passwordEncoder.encode(userPutRequest.getPassword());
        user.setPassword(securedPassword);

        user.setPhone(userPutRequest.getPhone());

        return toResponse(userRepository.save(user));
    }

    /**
     * Partially updates the user identified by {@code id}: only the non-null fields
     * present in {@code userPatchRequest} are applied; the rest are left unchanged.
     *
     * @param id               the ID of the user to update
     * @param userPatchRequest the fields to update; null fields are ignored
     * @return the updated user, mapped to its public representation
     * @throws RuntimeException if no user exists with the given id
     */
    public UserResponse patch(Long id, UserPatchRequest userPatchRequest) {
        User user = findById(id);

        if (userPatchRequest.getName() != null) user.setName(userPatchRequest.getName());
        if (userPatchRequest.getLastName() != null) user.setLastName(userPatchRequest.getLastName());

        if (userPatchRequest.getEmail() != null) user.setEmail(userPatchRequest.getEmail());

        if (userPatchRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userPatchRequest.getPassword()));
        }

        if (userPatchRequest.getPhone() != null) user.setPhone(userPatchRequest.getPhone());

        return toResponse(userRepository.save(user));
    }

    /**
     * Soft-deletes the user identified by {@code id}: marks it as deleted by setting
     * {@code deletedAt}, without removing the record from the database.
     *
     * @param id the ID of the user to soft-delete
     * @throws RuntimeException if no user exists with the given id
     */
    public void softDelete(Long id) {
        User user = findById(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Permanently removes the user identified by {@code id} from the database.
     * This operation is irreversible.
     *
     * @param id the ID of the user to permanently delete
     * @throws RuntimeException if no user exists with the given id
     */
    public void hardDelete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    // ── Métodos privados (trabajan con entidad) ───────────────────

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        return response;
    }
}
