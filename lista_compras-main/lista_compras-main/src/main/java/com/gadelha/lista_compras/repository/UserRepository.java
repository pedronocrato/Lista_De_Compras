package com.gadelha.lista_compras.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gadelha.lista_compras.model.User;

@Repository
public interface UserRepository extends JpaRepository<User , UUID> {
    
}
