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

/** Provides user management operations. */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
     */
    public UserResponse getById(Long id) {
        return toResponse(findById(id));
    }

    /**
     * Replaces every field of the user identified by {@code id}.
     *
     * @param id the ID of the user to update
     * @param userPutRequest the new values for all user fields
     * @return the updated user, mapped to its public representation
     */
    public UserResponse update(Long id, UserPutRequest userPutRequest) {
        User user = findById(id);

        user.setName(userPutRequest.getName());
        user.setLastName(userPutRequest.getLastName());
        user.setEmail(userPutRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userPutRequest.getPassword()));
        user.setPhone(userPutRequest.getPhone());

        return toResponse(userRepository.save(user));
    }

    /**
     * Partially updates the user identified by {@code id}.
     *
     * @param id the ID of the user to update
     * @param userPatchRequest the fields to update; null fields are ignored
     * @return the updated user, mapped to its public representation
     */
    public UserResponse patch(Long id, UserPatchRequest userPatchRequest) {
        User user = findById(id);

        if (userPatchRequest.getName() != null) {
            user.setName(userPatchRequest.getName());
        }
        if (userPatchRequest.getLastName() != null) {
            user.setLastName(userPatchRequest.getLastName());
        }
        if (userPatchRequest.getEmail() != null) {
            user.setEmail(userPatchRequest.getEmail());
        }
        if (userPatchRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userPatchRequest.getPassword()));
        }
        if (userPatchRequest.getPhone() != null) {
            user.setPhone(userPatchRequest.getPhone());
        }

        return toResponse(userRepository.save(user));
    }

    /**
     * Soft-deletes the user identified by {@code id}.
     *
     * @param id the ID of the user to soft-delete
     */
    public void softDelete(Long id) {
        User user = findById(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Permanently removes the user identified by {@code id}.
     *
     * @param id the ID of the user to permanently delete
     */
    public void hardDelete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

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
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setDeletedAt(user.getDeletedAt());
        return response;
    }
}
