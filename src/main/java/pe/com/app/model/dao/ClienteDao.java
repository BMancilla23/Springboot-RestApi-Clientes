package pe.com.app.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.com.app.model.entity.Cliente;

public interface ClienteDao extends JpaRepository<Cliente, Integer>{
    
}
