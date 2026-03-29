package com.autobots.automanager.repositorios;
//0712
import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.entidades.Telefone;

public interface TelefoneRepositorio extends JpaRepository <Telefone, Long> {

}
