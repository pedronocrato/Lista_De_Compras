package com.gadelha.lista_compras.dto;

import java.util.UUID;

public record ItemCompraRequestDTO(String nome , Integer quantidade , UUID listaDeComprasId) {
    
}
