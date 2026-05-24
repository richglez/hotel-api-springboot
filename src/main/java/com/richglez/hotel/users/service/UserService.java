package com.richglez.hotel.users.service;

import com.richglez.hotel.users.dto.UserPatchRequest;
import com.richglez.hotel.users.dto.UserPutRequest;
import com.richglez.hotel.users.dto.UserResponse;
import com.richglez.hotel.users.model.User;
import com.richglez.hotel.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ── Métodos públicos (devuelven DTO) ─────────────────────────

    public List<UserResponse> findAll() {
        return userRepository.findByDeletedAtIsNull()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public UserResponse update(Long id, UserPutRequest userPutRequest) {
        User user = findById(id);

        user.setName(userPutRequest.getName());
        user.setLastName(userPutRequest.getLastName());
        user.setEmail(userPutRequest.getEmail());
        user.setPassword(userPutRequest.getPassword());
        user.setPhone(userPutRequest.getPhone());

        return toResponse(userRepository.save(user));
    }

    public UserResponse patch(Long id, UserPatchRequest userPatchRequest) {
        User user = findById(id);

        if (userPatchRequest.getName() != null) user.setName(userPatchRequest.getName());
        if (userPatchRequest.getLastName() != null) user.setLastName(userPatchRequest.getLastName());

        if (userPatchRequest.getEmail() != null) user.setEmail(userPatchRequest.getEmail());

        if (userPatchRequest.getPassword() != null) user.setPassword(userPatchRequest.getPassword());

        if (userPatchRequest.getPhone() != null) user.setPhone(userPatchRequest.getPhone());

        return toResponse(userRepository.save(user));
    }

    public void softDelete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public void hardDelete(Long id) {
        User user = findById(id);
        user.setDeletedAt(LocalDateTime.now()); // soft delete
        userRepository.save(user);
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
