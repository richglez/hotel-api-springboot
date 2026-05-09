package com.richglez.hotel.repository;

import com.richglez.hotel.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{
}
