package com.gadelha.lista_compras.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gadelha.lista_compras.dto.ListaDeComprasRequestDTO;
import com.gadelha.lista_compras.dto.ListaDeComprasResponseDTO;
import com.gadelha.lista_compras.service.ListaDeComprasService;

@RestController
@RequestMapping("/api/listas")
public class ListaDeComprasController {
    
    private final ListaDeComprasService listaDeComprasService;

    public ListaDeComprasController(ListaDeComprasService listaDeComprasService){
        this.listaDeComprasService = listaDeComprasService;
    }

    @PostMapping
    public ResponseEntity<ListaDeComprasResponseDTO> criarLista(@RequestBody ListaDeComprasRequestDTO listaDeComprasRequestDTO){
        var user = listaDeComprasService.createList(listaDeComprasRequestDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ListaDeComprasResponseDTO>> listarListasDoUser(@PathVariable("userId") UUID userId){
        var listas = listaDeComprasService.listLists(userId);
        return ResponseEntity.ok(listas);
    }

    @GetMapping("/{listaDeComprasId}")
    public ResponseEntity<ListaDeComprasResponseDTO> buscarListaPorId(@PathVariable UUID listaDeComprasId) {
        return listaDeComprasService.findListById(listaDeComprasId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{listaDeComprasId}")
    public ResponseEntity<Void> deletarLista(@PathVariable("listaDeComprasId") UUID listaDeComprasId){
        listaDeComprasService.deleteList(listaDeComprasId);
        return ResponseEntity.noContent().build();
    }
}
