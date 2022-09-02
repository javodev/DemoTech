package com.demo.callengeTech.expose.web;

import com.demo.callengeTech.entity.NotaEntity;
import com.demo.callengeTech.entity.PersonEntity;
import com.demo.callengeTech.model.AuthenticationReq;
import com.demo.callengeTech.model.TokenInfo;
import com.demo.callengeTech.repository.NotaRepository;
import com.demo.callengeTech.repository.PersonRepository;
import com.demo.callengeTech.service.JwtUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    NotaRepository notaRepository;

    @RequestMapping(value = "/creausuario",
            produces = { "application/stream+json", "application/stream+json;charset=UTF-8", "application/json" },
            consumes = { "application/json", "application/json;charset=UTF-8"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody PersonEntity input){
        log.info("          :: CREA USUARIOS ::       ");
        return ResponseEntity.ok(personRepository.save(input));
    }

    @RequestMapping(value = "/creanotas/{idUsuario}",
            produces = { "application/stream+json", "application/stream+json;charset=UTF-8", "application/json" },
            consumes = { "application/json", "application/json;charset=UTF-8"},
            method = RequestMethod.POST)
    public ResponseEntity<?> createGrade(@RequestBody NotaEntity input, @PathVariable("idUsuario") String idUsuario){
        log.info("          :: CREA NOTAS ::       ");
        input.setPersonid(personRepository.findByPersonId(idUsuario).getId());

        return ResponseEntity.ok(notaRepository.save(input));
    }

    @RequestMapping(value = "/listNotas/{idUsuario}",
            produces = { "application/stream+json", "application/stream+json;charset=UTF-8", "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<List<NotaEntity>> listClientes(@PathVariable("idUsuario") Integer idUsuario){

        log.info("          :: LISTNOTAS ::       ");
        PersonEntity person = new PersonEntity();
        person.setId(idUsuario+"");
        return ResponseEntity.ok(notaRepository.findByPersonId(person.getId()));

    }

    @RequestMapping(value = "/publico/authenticate",
            method = RequestMethod.POST)
    public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {
        log.info("Autenticando al usuario {}", authenticationReq.getUsuario());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
                        authenticationReq.getClave()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                authenticationReq.getUsuario());

        final String jwt = jwtUtilService.generateToken(userDetails);

        TokenInfo tokenInfo = new TokenInfo(jwt);

        return ResponseEntity.ok(tokenInfo);
    }
}
