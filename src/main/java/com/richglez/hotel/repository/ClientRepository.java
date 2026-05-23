package com.richglez.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByDeletedAtIsNotNull();

    List<Client> findAllByDeletedAtIsNull();
}
