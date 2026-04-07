package com.gadelha.lista_compras.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.gadelha.lista_compras.model.ItemCompra;

public record ListaDeComprasResponseDTO(UUID listaDeComprasId , String name , LocalDate dataCriacao, List<ItemCompra> itens ,BigDecimal valorTotal , UUID userID) {
    
}
