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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneRepositorio repositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/")
    public List<Telefone> obterTelefones() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Telefone> selecionarPorId(@PathVariable Long id) {
        return repositorio.findById(id);
    }
    @PostMapping("/cadastro")
    public void cadastrarTelefone(@RequestBody Telefone Telefone) {
        repositorio.save(Telefone);
    }

    @PutMapping("/atualizar")
    public void atualizarTelefone(@RequestBody Telefone atualizacao) {
        Telefone Telefone = repositorio.getById(atualizacao.getId());
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        atualizador.atualizar(Telefone, atualizacao);
        repositorio.save(Telefone);
    }

    @DeleteMapping("/excluir")
    public void excluirTelefone(@RequestBody Telefone exclusao) {
        Telefone telefone = repositorio.getById(exclusao.getId());
        boolean telefoneCliente = false;
        List<Cliente> clientes = clienteRepositorio.findAll();
        for (Cliente cliente : clientes) {
            if (cliente.getTelefones().contains(telefone)) {
                cliente.getTelefones().remove(telefone);
                clienteRepositorio.save(cliente);
                telefoneCliente = true;
                break;
            }
        }
        if (!telefoneCliente) {
            repositorio.delete(telefone);
        }
    }
}