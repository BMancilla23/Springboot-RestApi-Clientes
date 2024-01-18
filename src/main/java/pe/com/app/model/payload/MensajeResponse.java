package pe.com.app.model.payload;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeResponse<T> implements Serializable{
    
    private String mensaje;
    private T object;
    private String error;
    
    public MensajeResponse(String mensaje, T object) {
        this.mensaje = mensaje;
        this.object = object;
    }

    
    
}




