package pe.com.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pe.com.app.model.dto.ClienteDto;

public interface IClienteService {

    ClienteDto save(ClienteDto cliente);

    ClienteDto findById(Integer id);

    void delete(ClienteDto cliente);
    
    boolean existsById(Integer id);

    Page<ClienteDto> findAll(Pageable pageable);

}



