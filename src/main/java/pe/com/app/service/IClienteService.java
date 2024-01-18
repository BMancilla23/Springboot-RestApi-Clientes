package pe.com.app.service;

import java.util.List;

import pe.com.app.model.dto.ClienteDto;
import pe.com.app.model.entity.Cliente;

public interface IClienteService {

    Cliente save(ClienteDto cliente);

    Cliente findById(Integer id);

    void delete(Cliente cliente);
    
    boolean existsById(Integer id);

}


