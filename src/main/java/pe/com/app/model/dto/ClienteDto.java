package pe.com.app.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ClienteDto implements Serializable{
    
    private Integer idCliente;

    private String nombre;

    private String apellido;
   
    private String correo;

    private LocalDateTime fechaRegistro;
}

