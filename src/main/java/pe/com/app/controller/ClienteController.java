package pe.com.app.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.com.app.model.dto.ClienteDto;
import pe.com.app.model.entity.Cliente;
import pe.com.app.model.payload.MensajeResponse;
import pe.com.app.service.IClienteService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService clienteService;

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MensajeResponse<ClienteDto>> create(@RequestBody ClienteDto clienteDto) {
        try {
            Cliente clienteSave = clienteService.save(clienteDto);
            ClienteDto clienteResponse = ClienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .correo(clienteSave.getCorreo())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .build();
            return new ResponseEntity<>(new MensajeResponse<>("Cliente creado correctamente", clienteResponse),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(new MensajeResponse<>("Error al crear el cliente", null, exDt.getMessage()),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MensajeResponse<ClienteDto>> update(@RequestBody ClienteDto clienteDto,
            @PathVariable Integer id) {
        try {

            if (clienteService.existsById(id)) {

                clienteDto.setIdCliente(id);
                Cliente clienteUpdate = clienteService.save(clienteDto);
                ClienteDto clienteResponse = ClienteDto.builder()
                        .idCliente(clienteUpdate.getIdCliente())
                        .nombre(clienteUpdate.getNombre())
                        .apellido(clienteUpdate.getApellido())
                        .correo(clienteUpdate.getCorreo())
                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                        .build();

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
        /* Map<String, Object> response = new HashMap<>(); */

        try {
            Cliente clienteDelete = clienteService.findById(id);

            if (clienteDelete != null) {
                clienteService.delete(clienteDelete);
                /*
                 * response.put("mensaje", "Cliente eliminado correctamente");
                 * response.put("cliente", clienteDelete);
                 */
                return new ResponseEntity<>(new MensajeResponse<>("Cliente eliminado correctamente", null),
                        HttpStatus.NO_CONTENT);
            } else {
                /*
                 * response.put("mensaje", "Cliente con ID " + id + " no encontrado");
                 * response.put("cliente", null);
                 */
                return new ResponseEntity<>(new MensajeResponse<>("Cliente con ID " + id + " no encontrado", null),
                        HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exDt) {
            /*
             * response.put("mensaje", "Error al eliminar el cliente");
             * response.put("error", exDt.getMessage());
             */
            return new ResponseEntity<>(new MensajeResponse<>("Error al eliminar el cliente", null, exDt.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MensajeResponse<ClienteDto>> showById(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.findById(id);
            if (cliente != null) {
                ClienteDto clienteResponse = ClienteDto.builder()
                        .idCliente(cliente.getIdCliente())
                        .nombre(cliente.getNombre())
                        .apellido(cliente.getApellido())
                        .correo(cliente.getCorreo())
                        .fechaRegistro(cliente.getFechaRegistro())
                        .build();
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

}
