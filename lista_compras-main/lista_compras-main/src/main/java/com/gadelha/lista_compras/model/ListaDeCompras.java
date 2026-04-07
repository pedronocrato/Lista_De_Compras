package com.gadelha.lista_compras.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "listas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaDeCompras {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID listaDeComprasId;

    @Column(name = "name")
    private String name;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao = LocalDate.now();

    @Column(name = "valorTotal")
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  
    private User user;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  
    private List<ItemCompra> itensCompras;
}
