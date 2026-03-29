package com.autobots.automanager.modelo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Component
public class ClienteSelecionador {
	@Autowired 
	private ClienteRepositorio repositorio;
	public Optional<Cliente> selecionarPorId(Long id){
		return repositorio.findById(id);
	}
	}
