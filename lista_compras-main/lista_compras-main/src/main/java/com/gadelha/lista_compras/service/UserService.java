package com.gadelha.lista_compras.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gadelha.lista_compras.dto.UpdateUserDTO;
import com.gadelha.lista_compras.dto.UserRequestDTO;
import com.gadelha.lista_compras.dto.UserResponseDTO;
import com.gadelha.lista_compras.model.User;
import com.gadelha.lista_compras.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO){
        var cliente = new User(
            null, 
            userRequestDTO.name(), 
            userRequestDTO.email(), 
            userRequestDTO.password(),
            null);

        var salvo = userRepository.save(cliente);

        return new UserResponseDTO(salvo.getUserId(), salvo.getName(), salvo.getEmail());
    }

    public List<UserResponseDTO> listUsers(){
        return userRepository.findAll().stream()
                            .map(user -> new UserResponseDTO(
                                user.getUserId(),
                                user.getName(), 
                                user.getEmail()
                                ))
                            .toList();

     
    }

    public Optional<UserResponseDTO> findUserById(UUID userID){
        return userRepository.findById(userID)
                            .map(user -> new UserResponseDTO(
                                user.getUserId(), 
                                user.getName(), 
                                user.getEmail()));
    }

    public void deleteUserByID(UUID userID){
        var userExists = userRepository.existsById(userID);

        if(userExists){
            userRepository.deleteById(userID);
        }
    }

    public void updateUserById(UUID userID , UpdateUserDTO updateUserDTO){
        var userEntity = userRepository.findById(userID);

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDTO.name() != null){
                user.setName(updateUserDTO.name());
            }

            if(updateUserDTO.password() != null){
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }
                                                
    }



    
}
