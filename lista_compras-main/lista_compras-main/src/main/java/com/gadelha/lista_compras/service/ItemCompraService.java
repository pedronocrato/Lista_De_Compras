package com.gadelha.lista_compras.service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.gadelha.lista_compras.dto.ItemCompraRequestDTO;
import com.gadelha.lista_compras.dto.ItemCompraResponseDTO;
import com.gadelha.lista_compras.model.ItemCompra;
import com.gadelha.lista_compras.model.ListaDeCompras;
import com.gadelha.lista_compras.repository.ItemCompraRepository;
import com.gadelha.lista_compras.repository.ListaDeComprasRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ItemCompraService {

    private final ItemCompraRepository itemCompraRepository;
    private final ListaDeComprasRepository listaDeComprasRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ItemCompraService(ItemCompraRepository itemCompraRepository,
                             ListaDeComprasRepository listaDeComprasRepository) {
        this.itemCompraRepository = itemCompraRepository;
        this.listaDeComprasRepository = listaDeComprasRepository;
    }

    public ItemCompraResponseDTO addItenList(ItemCompraRequestDTO itemCompraRequestDTO) {

        ListaDeCompras lista = listaDeComprasRepository.findById(itemCompraRequestDTO.listaDeComprasId())
                .orElseThrow(() -> new EntityNotFoundException("Lista não encontrada"));

        String nomeCodificado = URLEncoder.encode(itemCompraRequestDTO.nome(), StandardCharsets.UTF_8);
        String searchUrl = "https://dummyjson.com/products/search?q=" + nomeCodificado;
        JsonNode resposta = restTemplate.getForObject(URI.create(searchUrl), JsonNode.class);

        if (resposta == null || !resposta.has("products") || resposta.get("products").isEmpty()) {
            throw new EntityNotFoundException("Produto não encontrado na DummyJSON API");
        }

        JsonNode produto = resposta.get("products").get(0);

        String nomeProduto = produto.get("title").asText();
        BigDecimal preco = new BigDecimal(produto.get("price").asText());
        String linkImagem = produto.get("thumbnail").asText();

        ItemCompra item = new ItemCompra(
                null,
                nomeProduto,
                itemCompraRequestDTO.quantidade(),
                preco,
                linkImagem,
                lista
        );

        ItemCompra itemSalvo = itemCompraRepository.save(item);

        
        lista.setValorTotal(
                lista.getValorTotal().add(preco.multiply(BigDecimal.valueOf(itemCompraRequestDTO.quantidade())))
        );
        listaDeComprasRepository.save(lista);

        return new ItemCompraResponseDTO(
                itemSalvo.getItemId(),
                itemSalvo.getNome(),
                itemSalvo.getQuantidade(),
                itemSalvo.getPreco(),
                itemSalvo.getLinkProduto(),
                itemSalvo.getLista().getListaDeComprasId()
        );
    }

    public List<ItemCompraResponseDTO> buscarProdutosNaApiPorNome(String nome) {

        String searchUrl = "https://dummyjson.com/products/search?q=" + nome;
        JsonNode resposta = restTemplate.getForObject(URI.create(searchUrl), JsonNode.class);

        if (resposta == null || !resposta.has("products") || resposta.get("products").isEmpty()) {
                return new ArrayList<>(); 
        }

        List<ItemCompraResponseDTO> listaProdutos = new ArrayList<>();

        for (JsonNode produto : resposta.get("products")) {
                String nomeProduto = produto.get("title").asText();
                BigDecimal preco = new BigDecimal(produto.get("price").asText());
                String linkImagem = produto.get("thumbnail").asText();

                listaProdutos.add(new ItemCompraResponseDTO(
                        null, 
                        nomeProduto,
                        1, 
                        preco,
                        linkImagem,
                        null 
                ));
        }

        return listaProdutos;
    }      

        @Transactional
        public void removerItem(UUID listaId, UUID itemId) {
                ItemCompra item = itemCompraRepository.findById(itemId)
                        .orElseThrow(() -> new EntityNotFoundException("Item não encontrado"));

                
                if (!item.getLista().getListaDeComprasId().equals(listaId)) {
                        throw new IllegalArgumentException("O item não pertence à lista informada.");
                }

                ListaDeCompras lista = item.getLista();

                
                BigDecimal valorRemover = item.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                lista.setValorTotal(lista.getValorTotal().subtract(valorRemover));

                listaDeComprasRepository.save(lista);
                itemCompraRepository.delete(item);
        }
}
