package co.edu.uniquindio.uniclinica.servicios.implementacion;

import co.edu.uniquindio.uniclinica.dto.seguridad.LoginDTO;
import co.edu.uniquindio.uniclinica.modelo.entidades.Cuenta;
import co.edu.uniquindio.uniclinica.servicios.interfaces.AutenticacionServicio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class AutenticacionServicioImpl implements AutenticacionServicio {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    @Override
    public JwtDTO login(LoginDTO loginDTO) throws Exception {
        Authentication Autentoken = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.password()
        );
        var usuarioAutenticado = authenticationManager.authenticate(Autentoken);
        var JWTtoken = tokenService.generarToken((Cuenta) usuarioAutenticado.getPrincipal());
        return new JwtDTO(JWTtoken);//El controller recibirá este token y hará un return ResponseEntity.ok(login)
    }
}
