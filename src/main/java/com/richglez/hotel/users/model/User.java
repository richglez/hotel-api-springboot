package com.richglez.hotel.users.model;

import com.richglez.hotel.common.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * JPA entity representing a registered user. Implements {@link UserDetails} so it can be
 * used directly by Spring Security for authentication and authorization.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) // registry createdAt, updatedDate, deletedData etc
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // not null
    private String name;

    @Column(nullable = false)  // not null
    private String lastName;

    @Column(nullable = false, unique = true) // not null & unique
    private String email;

    @Column(nullable = false) // not null
    private String password;

    private String phone; // null value

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt; // auditory

    @LastModifiedDate
    private LocalDateTime updatedAt; // auditory

    private LocalDateTime deletedAt; // soft delete

    // --- UserDetails methods ---

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of(); // Evita el error si el rol es nulo
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    @NonNull
    public String getUsername() {
        return email;
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @SuppressWarnings("RedundantMethodOverride")
    @Override
    public boolean isEnabled() {
        return true;
    }
}