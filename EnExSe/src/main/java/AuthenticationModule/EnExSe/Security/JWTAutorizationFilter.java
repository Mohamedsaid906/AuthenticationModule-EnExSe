package AuthenticationModule.EnExSe.Security;

import AuthenticationModule.EnExSe.Utils.SecParam;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAutorizationFilter extends OncePerRequestFilter {
    /****cette classe pour verifier l'athenticite de token*****/
    /***extraire le token de request***/
    /***verifier l'authenticite de token***/
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //this code to solve probleme of cors policy

        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers,Origin,Accept," + "X-Requested-With, Content-Type, Access-Control-Request-Method ," + "Access-Control-Request-Headers, Authorisation");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Authorisation, Access-ControlAllow-Origin,Access-Control-Allow-Credentials ");
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        /**get token d'apres le request**/
        String jwt = httpServletRequest.getHeader("Authorisation");

        if (jwt == null || !jwt.startsWith(SecParam.Prefixe)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
            /**passer à la filtre suivant***/

        }

        /***verifier l'authenticite de token ***/
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecParam.Secret)).build();

        /**Enlever le prefixe de token **/
        jwt = jwt.substring(SecParam.Prefixe.length());


        /**Decoder le token apres enlever le prifixe***/

        DecodedJWT decodedJWT = verifier.verify(jwt);/**verifier le signature de token ***/

        /***extraire le claims de notre token***/
        String username = decodedJWT.getSubject();/**extraire le username**/
        List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);/***extraire les roles**/
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String r : roles) {
            authorities.add(new SimpleGrantedAuthority(r));

            /***mettre a jour le contexte de springsecurity**/
            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(httpServletRequest, httpServletResponse);


        }


    }

}
