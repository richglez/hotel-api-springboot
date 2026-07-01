package com.richglez.hotel.users.controller;

import com.richglez.hotel.users.dto.UserPatchRequest;
import com.richglez.hotel.users.dto.UserPutRequest;
import com.richglez.hotel.users.dto.UserResponse;
import com.richglez.hotel.users.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Exposes user management endpoints. */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** Returns all active users. */
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /** Returns a user by id. */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    /** Replaces a user by id. */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserPutRequest userPutRequest) {
        return ResponseEntity.ok(userService.update(id, userPutRequest));
    }

    /** Patches a user by id. */
    @PatchMapping("{id}")
    public ResponseEntity<UserResponse> patch(
            @PathVariable Long id,
            @Valid @RequestBody UserPatchRequest userPatchRequest) {
        return ResponseEntity.ok(userService.patch(id, userPatchRequest));
    }

    /** Deletes a user by id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
