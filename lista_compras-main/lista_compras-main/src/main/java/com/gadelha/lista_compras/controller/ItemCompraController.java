package com.gadelha.lista_compras.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gadelha.lista_compras.dto.ItemCompraRequestDTO;
import com.gadelha.lista_compras.dto.ItemCompraResponseDTO;
import com.gadelha.lista_compras.service.ItemCompraService;

@RestController
@RequestMapping("/api/itens")
public class ItemCompraController {
    
    private final ItemCompraService itemCompraService;

    public ItemCompraController(ItemCompraService itemCompraService) {
        this.itemCompraService = itemCompraService;
    }

    
    @PostMapping
    public ResponseEntity<ItemCompraResponseDTO> adicionarItem(@RequestBody ItemCompraRequestDTO itemCompraRequestDTO){
        ItemCompraResponseDTO itemCompraResponseDTO = itemCompraService.addItenList(itemCompraRequestDTO);
        return ResponseEntity.ok(itemCompraResponseDTO);
    }

    
    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<ItemCompraResponseDTO>> buscarProdutosPorNome(@PathVariable String nome){
        List<ItemCompraResponseDTO> produtos = itemCompraService.buscarProdutosNaApiPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{listaId}/{itemId}")
    public ResponseEntity<Void> removerItem(
            @PathVariable UUID listaId,
            @PathVariable UUID itemId) {

        itemCompraService.removerItem(listaId, itemId);
        return ResponseEntity.noContent().build();
    }

}
