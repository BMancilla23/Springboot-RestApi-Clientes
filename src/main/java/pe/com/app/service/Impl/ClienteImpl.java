package pe.com.app.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pe.com.app.model.dao.ClienteDao;
import pe.com.app.model.dto.ClienteDto;
import pe.com.app.model.entity.Cliente;
import pe.com.app.service.IClienteService;

@Service
@RequiredArgsConstructor
public class ClienteImpl implements IClienteService {

    private final ClienteDao clienteDao;

    @Transactional
    @Override
    public ClienteDto save(ClienteDto clienteDto) {
        Cliente cliente = Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();
        Cliente savedCliente = clienteDao.save(cliente);

        return ClienteDto.builder()
                .idCliente(savedCliente.getIdCliente())
                .nombre(savedCliente.getNombre())
                .apellido(savedCliente.getApellido())
                .correo(savedCliente.getCorreo())
                .fechaRegistro(savedCliente.getFechaRegistro())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ClienteDto findById(Integer id) {
        Cliente cliente = clienteDao.findById(id).orElse(null);
        if (cliente != null) {
            return ClienteDto.builder()
                    .idCliente(cliente.getIdCliente())
                    .nombre(cliente.getNombre())
                    .apellido(cliente.getApellido())
                    .correo(cliente.getCorreo())
                    .fechaRegistro(cliente.getFechaRegistro())
                    .build();
        }
        return null;
    }

    @Transactional
    @Override
    public void delete(ClienteDto clienteDto) {
        // Convertir ClienteDto a Cliente
        Cliente cliente = Cliente.builder()
                .idCliente(clienteDto.getIdCliente())
                .nombre(clienteDto.getNombre())
                .apellido(clienteDto.getApellido())
                .correo(clienteDto.getCorreo())
                .fechaRegistro(clienteDto.getFechaRegistro())
                .build();

        // Llamar al método delete del repositorio
        clienteDao.delete(cliente);
    }

    @Override
    public boolean existsById(Integer id) {
        return clienteDao.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClienteDto> findAll(Pageable pageable) {
        // Obtener la página de clientes utilizando el repositorio (ClienteDao)
        Page<Cliente> clientesPage = clienteDao.findAll(pageable);

        // Convertir la página de entidades a una página de DTOs
        return clientesPage.map(cliente -> ClienteDto.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .correo(cliente.getCorreo())
                .fechaRegistro(cliente.getFechaRegistro())
                .build());
    }
}
