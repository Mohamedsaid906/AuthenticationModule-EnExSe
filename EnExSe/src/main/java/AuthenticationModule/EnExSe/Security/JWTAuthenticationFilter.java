package AuthenticationModule.EnExSe.Security;

import AuthenticationModule.EnExSe.Entities.User;
import AuthenticationModule.EnExSe.Utils.SecParam;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    /***serialisation de JSON de requet HTTP de client vers USER.JAVA ***/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }

    /****Construction de SpringUser****/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User SpringUser = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

        List<String> roles = new ArrayList<>();
        SpringUser.getAuthorities().forEach(au -> {
            roles.add(au.getAuthority());

        });

        /***construction de Token ****/
        /**Cle de signature**/
        String Jwt = JWT.create().withSubject(SpringUser.getUsername()).withArrayClaim("roles", roles.toArray(new String[roles.size()])).withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).sign(Algorithm.HMAC256(SecParam.Secret));


        /****Retourner le Jwt dans la reponce ***/
        response.addHeader("Authorisation", Jwt);


    }
}

