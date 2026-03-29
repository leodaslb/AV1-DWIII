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
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

    @Autowired
    private DocumentoRepositorio repositorio;
    @Autowired
    private DocumentoAtualizador atualizador;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/documentos")
    public List<Documento> obterDocumentos() {
        return repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Documento> selecionarPorId(@PathVariable Long id) {
        return repositorio.findById(id);
    }
    @PostMapping("/cadastro")
    public void cadastrarDocumento(@RequestBody Documento documento) {
        repositorio.save(documento);
    }

    @PutMapping("/atualizar")
    public void atualizarDocumento(@RequestBody Documento atualizacao) {
        Documento documento = repositorio.getById(atualizacao.getId());
        DocumentoAtualizador atualizador = new DocumentoAtualizador();
        atualizador.atualizar(documento, atualizacao);
        repositorio.save(documento);
    }
    //Professor confeço q tive q usar o gepeteco nessa parte :(
    @DeleteMapping("/excluir")
    public void excluirDocumento(@RequestBody Documento exclusao) {
        Documento documento = repositorio.getById(exclusao.getId());
        boolean documentoCliente = false;
        List<Cliente> clientes = clienteRepositorio.findAll();
        for (Cliente cliente : clientes) {
           if (cliente.getDocumentos().contains(documento)) {
                
                cliente.getDocumentos().remove(documento);
                clienteRepositorio.save(cliente);
                documentoCliente = true;
                break; 
                }
            }
        }

}