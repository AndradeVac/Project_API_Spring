package com.apiLogin.exemplo.controllers;

import com.apiLogin.exemplo.model.entities.Usuario;
import com.apiLogin.exemplo.model.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping(value = "/login")
    
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuarioRequest){

//        EXEMPLO REQUEST USUARIO
//        {
//            "username": "teste@gmail.com",
//                "senha": "exemplo123"
//        }

        try {

            Optional<Usuario> usuario = usuarioRepository.findByEmailOrUsername(usuarioRequest.getUsername(), usuarioRequest.getUsername());


            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Control-Allow-Origin", "*");


            if(usuario.isPresent()){

                if(usuario.get().equals(usuarioRequest)){
                    return ResponseEntity.ok().headers(headers).build();
                }else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
                }

            }else{
                return ResponseEntity.notFound().headers(headers).build();
            }
   
        }catch (Exception e){
            throw e;
        }


    }
    
    @PostMapping(value = "/cadastro")
    
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario novoUsuario){
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Access-Control-Allow-Origin", "*");
    	
         try {
        	 
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmailOrUsername
            		(novoUsuario.getEmail(), novoUsuario.getUsername());

            if (usuarioExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).build(); 
            } else {
                Usuario usuarioCadastrado = usuarioRepository.save(novoUsuario);
                return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(usuarioCadastrado);
            }
        }
         catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
           
            
            //   USUARIO DE TESTE
            //	 "usuario": "exemplo",
            //   "email": "exemplo@example.com",
            //   "senha": "exemplo123",
         
        }
    }
}

    
    
    
    
    
    
    
    
    
    
    	
    
    

    
    


