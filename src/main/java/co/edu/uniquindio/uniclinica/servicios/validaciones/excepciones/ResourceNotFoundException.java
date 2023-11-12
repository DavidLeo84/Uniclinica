package co.edu.uniquindio.uniclinica.servicios.validaciones.excepciones;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
