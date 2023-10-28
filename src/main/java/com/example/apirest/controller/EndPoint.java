// Importações de bibliotecas e classes necessárias
package com.example.apirest.controller;

import com.example.apirest.Repository.ClientesRepository;
import com.example.apirest.model.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Define esta classe como um controlador REST
@RestController
@RequestMapping("/cliente")
public class EndPoint {

    // Injeção de dependência do repositório de clientes
    @Autowired
    private ClientesRepository cr;

    // Define um endpoint para listar todos os clientes (método HTTP GET)
    @GetMapping
    public List<Clientes> listarClientes(){
        // Obtém uma lista de todos os clientes do repositório
        List<Clientes> clientes = cr.findAll();
        return clientes; // Retorna a lista de clientes como resposta
    }

    // Define um endpoint para criar um novo cliente (método HTTP POST)
    @PostMapping("/clientes")
    public ResponseEntity<Clientes> criarCliente(@RequestBody Clientes clientes){
        // Salva o novo cliente no repositório
        Clientes clienteCriado = cr.save(clientes);
        return ResponseEntity.ok(clienteCriado); // Retorna o cliente criado como resposta
    }

    // Define um endpoint para atualizar um cliente existente (método HTTP PUT)
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Clientes> atualizarCliente(@PathVariable Long id, @RequestBody Clientes cliente) {
        // Verifica se o cliente com o ID especificado existe no repositório
        if (!cr.existsById(id)) {
            return ResponseEntity.notFound().build(); // Retorna uma resposta 404 se o cliente não existir
        }

        cliente.setId(id); // Define o ID do cliente a ser atualizado

        // Salva o cliente atualizado no repositório
        Clientes clienteAtualizado = cr.save(cliente);

        return ResponseEntity.ok(clienteAtualizado); // Retorna o cliente atualizado como resposta
    }

    // Define um endpoint para excluir um cliente (método HTTP DELETE)
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id){
        // Verifica se o cliente com o ID especificado existe no repositório
        if (!cr.existsById(id)){
            return ResponseEntity.notFound().build(); // Retorna uma resposta 404 se o cliente não existir
        }
        cr.deleteById(id); // Exclui o cliente do repositório
        return ResponseEntity.notFound().build(); // Retorna uma resposta 404 após a exclusão
    }
}
