package com.gadelha.lista_compras.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gadelha.lista_compras.dto.ListaDeComprasRequestDTO;
import com.gadelha.lista_compras.dto.ListaDeComprasResponseDTO;
import com.gadelha.lista_compras.model.ListaDeCompras;
import com.gadelha.lista_compras.model.User;
import com.gadelha.lista_compras.repository.ListaDeComprasRepository;
import com.gadelha.lista_compras.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ListaDeComprasService {
    
    private final ListaDeComprasRepository listaDeComprasRepository;
    private final UserRepository userRepository;

    public ListaDeComprasService(ListaDeComprasRepository listaDeComprasRepository, UserRepository userRepository) {
        this.listaDeComprasRepository = listaDeComprasRepository;
        this.userRepository = userRepository;
    }

    public ListaDeComprasResponseDTO createList(ListaDeComprasRequestDTO listaDeComprasRequestDTO){
        var user = userRepository.findById(listaDeComprasRequestDTO.userId())
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

        var lista = new ListaDeCompras(
            null, 
            listaDeComprasRequestDTO.name(), 
            LocalDate.now(), 
            BigDecimal.ZERO, 
            user, 
            null);
        ListaDeCompras novaLista = listaDeComprasRepository.save(lista);
        return new ListaDeComprasResponseDTO(
            novaLista.getListaDeComprasId(), 
            novaLista.getName(), 
            novaLista.getDataCriacao(), 
            novaLista.getItensCompras(), 
            novaLista.getValorTotal(), 
            novaLista.getUser().getUserId());
    }

    public List<ListaDeComprasResponseDTO> listLists(UUID userId){
        var user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        
        return user.getListasDeCompras()
                .stream()
                .map(list -> new ListaDeComprasResponseDTO(
                    list.getListaDeComprasId(), 
                    list.getName(), 
                    list.getDataCriacao(),
                    list.getItensCompras(), 
                    list.getValorTotal(), 
                    list.getUser().getUserId()))
                .toList();
    }

    public Optional<ListaDeComprasResponseDTO> findListById(UUID listaDeComprasId){
        return listaDeComprasRepository.findById(listaDeComprasId)
            .map(list -> new ListaDeComprasResponseDTO(
                    list.getListaDeComprasId(), 
                    list.getName(), 
                    list.getDataCriacao(),
                    list.getItensCompras(), 
                    list.getValorTotal(), 
                    list.getUser().getUserId()));
    }

    public void deleteList(UUID listaDeComprasId){
        var listaExists = listaDeComprasRepository.existsById(listaDeComprasId);

        if(listaExists){
            listaDeComprasRepository.deleteById(listaDeComprasId);
        }
    }



    
}
