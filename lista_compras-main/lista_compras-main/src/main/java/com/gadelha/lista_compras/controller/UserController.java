package com.gadelha.lista_compras.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gadelha.lista_compras.dto.UpdateUserDTO;
import com.gadelha.lista_compras.dto.UserRequestDTO;
import com.gadelha.lista_compras.dto.UserResponseDTO;
import com.gadelha.lista_compras.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> salvarCliente(@RequestBody UserRequestDTO userRequestDTO){
        var user = userService.saveUser(userRequestDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listarClientes(){
        var users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> buscarClientePeloId(@PathVariable("userId") UUID userId){
        var user = userService.findUserById(userId);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletarCliente(@PathVariable("userId") UUID userID){
        userService.deleteUserByID(userID);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userID}")
    public ResponseEntity<Void> atualizarCLiente(@PathVariable("userID") UUID userID, 
                                                @RequestBody UpdateUserDTO updateUserDTO){
        userService.updateUserById(userID, updateUserDTO);
        return ResponseEntity.noContent().build();
    }

}
