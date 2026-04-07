package com.gadelha.lista_compras.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gadelha.lista_compras.dto.ListaDeComprasResponseDTO;
import com.gadelha.lista_compras.model.ListaDeCompras;

@Repository
public interface ListaDeComprasRepository extends JpaRepository<ListaDeCompras , UUID>{
   
}
