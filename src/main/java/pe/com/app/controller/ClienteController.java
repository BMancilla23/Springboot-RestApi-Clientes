package pe.com.app.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.com.app.model.dto.ClienteDto;
import pe.com.app.model.payload.MensajeResponse;
import pe.com.app.service.IClienteService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService clienteService;

    @PostMapping("/cliente")
    public ResponseEntity<MensajeResponse<ClienteDto>> create(@RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto clienteResponse = clienteService.save(clienteDto);
            return new ResponseEntity<>(new MensajeResponse<>("Cliente creado correctamente", clienteResponse),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(new MensajeResponse<>("Error al crear el cliente", null, exDt.getMessage()),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<MensajeResponse<ClienteDto>> update(@RequestBody ClienteDto clienteDto,
            @PathVariable Integer id) {
        try {
            if (clienteService.existsById(id)) {
                clienteDto.setIdCliente(id);
                ClienteDto clienteResponse = clienteService.save(clienteDto);
                return new ResponseEntity<>(new MensajeResponse<>("Cliente actualizado correctamente", clienteResponse),
                        HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new MensajeResponse<>("Cliente no encontrado con ID " + id, null),
                    HttpStatus.NOT_FOUND);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    new MensajeResponse<>("Error al actualizar el cliente", null, exDt.getMessage()),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<MensajeResponse<Void>> delete(@PathVariable Integer id) {
        try {
            ClienteDto clienteDelete = clienteService.findById(id);
            if (clienteDelete != null) {
                clienteService.delete(clienteDelete);
                return new ResponseEntity<>(new MensajeResponse<>("Cliente eliminado correctamente", null),
                        HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(new MensajeResponse<>("Cliente con ID " + id + " no encontrado", null),
                        HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(new MensajeResponse<>("Error al eliminar el cliente", null, exDt.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<MensajeResponse<ClienteDto>> showById(@PathVariable Integer id) {
        try {
            ClienteDto clienteResponse = clienteService.findById(id);
            if (clienteResponse != null) {
                return new ResponseEntity<>(new MensajeResponse<>("Cliente encontrado correctamente", clienteResponse),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MensajeResponse<>("Cliente con ID " + id + " no encontrado", null),
                        HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(new MensajeResponse<>("Error al obtener el cliente", null, exDt.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("clientes")
    public ResponseEntity<MensajeResponse<Page<ClienteDto>>> showAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idCliente") String sortBy) {

        try {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortBy).ascending());
            Page<ClienteDto> resultPage = clienteService.findAll(pageable);
            return new ResponseEntity<>(new MensajeResponse<>("Lista de cliente obtenido exitosamente", resultPage), HttpStatus.OK);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(new MensajeResponse<>("Error al obtener la lista de clientes", null, exDt.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
