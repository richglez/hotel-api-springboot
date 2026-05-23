package com.richglez.hotel.controller;

import com.richglez.hotel.dto.UserPatchRequest;
import com.richglez.hotel.dto.UserPutRequest;
import com.richglez.hotel.dto.UserResponse;
import com.richglez.hotel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserPutRequest userPutRequest) {
        return ResponseEntity.ok(userService.update(id, userPutRequest));
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserResponse> patch(@PathVariable Long id, @Valid @RequestBody UserPatchRequest userPatchRequest) {
        return ResponseEntity.ok(userService.patch(id, userPatchRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}

// No hay POST /users — el registro de usuarios vive en AuthController, así que UserController solo hace CRUD sobre usuarios ya existentes. Esto es la separación correcta.