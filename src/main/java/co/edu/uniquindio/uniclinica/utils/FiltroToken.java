package co.edu.uniquindio.uniclinica.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Filter;

@Component
@RequiredArgsConstructor
public class FiltroToken implements Filter {

    private final JWTUtils jwtUtils;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = getToken(req);
        try{
            if (token != null) {
                Jws<Claims> jws = jwtUtils.parseJwt(token);
//Acá haremos las validaciones de autorización de acuerdo al rol del usuario
                System.out.println(jws.getBody().getSubject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }
    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
    }
}
