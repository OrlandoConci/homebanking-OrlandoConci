package com.mindhub.homebanking.controladores;

import com.mindhub.homebanking.modelos.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mindhub.homebanking.repositorios.ClienteRepositorio;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/hello")
    public String getClientes(){
        return "Hola Clientes";
    }

    @GetMapping("/")
    public List<Cliente> getAllClientes(){
        return clienteRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public Cliente getOneClientById(@PathVariable Long id){
        return clienteRepositorio.findById(id).orElse(null);
    }
}
