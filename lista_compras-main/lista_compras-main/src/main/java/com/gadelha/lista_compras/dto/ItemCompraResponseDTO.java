package com.gadelha.lista_compras.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCompraResponseDTO( UUID itemId,
    String nome,
    Integer quantidade,
    BigDecimal preco,
    String linkProduto,
    UUID listaId) {
    
}
