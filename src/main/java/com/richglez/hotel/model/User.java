package com.richglez.hotel.model;

import com.richglez.hotel.enums.Roles;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    // --- UserDetails ---

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return List.of(); // Eviita el error si el rol es nulo
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