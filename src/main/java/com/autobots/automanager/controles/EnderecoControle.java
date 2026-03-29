package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {

    @Autowired
    private EnderecoRepositorio repositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/enderecos")
    public List<Endereco> obterEnderecos() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Endereco> selecionarPorId(@PathVariable Long id) {
        return repositorio.findById(id);
    }
    @PostMapping("/cadastro")
    public void cadastrarEndereco(@RequestBody Endereco Endereco) {
        repositorio.save(Endereco);
    }

    @PutMapping("/atualizar")
    public void atualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco Endereco = repositorio.getById(atualizacao.getId());
        EnderecoAtualizador atualizador = new EnderecoAtualizador();
        atualizador.atualizar(Endereco, atualizacao);
        repositorio.save(Endereco);
    }
    
    @DeleteMapping("/excluir")
    public void excluirEndereco(@RequestBody Endereco exclusao) {
        Endereco Endereco = repositorio.getById(exclusao.getId());
        boolean enderecoCliente = false;
        List<Cliente> clientes = clienteRepositorio.findAll();
        for (Cliente cliente : clientes) {
            if (cliente.getEndereco() != null && cliente.getEndereco().getId().equals(Endereco.getId())) {
                cliente.setEndereco(null);
                clienteRepositorio.save(cliente);
                enderecoCliente = true;
                break;
            }
        }
        if (!enderecoCliente) {
            repositorio.delete(Endereco);
        }
    }

}