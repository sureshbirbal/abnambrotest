package nl.abnambro.recipes.controller;

import nl.abnambro.recipes.config.JwtTokenRepository;
import nl.abnambro.recipes.config.JwtTokenUtil;
import nl.abnambro.recipes.data.JwtRequest;
import nl.abnambro.recipes.data.JwtResponse;
import nl.abnambro.recipes.data.UserData;
import nl.abnambro.recipes.exception.UserExistsException;
import nl.abnambro.recipes.service.UserService;
import nl.abnambro.recipes.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserData user) {
        if (user != null) {
            if (getUserService().findUserByUserName(user.getUsername()) == null) {
                return ResponseEntity.ok(getUserDetailsService().save(user));
            } else {
                throw new UserExistsException("User Already Exists");
            }
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
            throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = getUserDetailsService()
                .loadUserByUsername(authenticationRequest.getUsername());
        String token = getJwtTokenRepository().findToken(userDetails.getUsername());
        if (token == null) {
            token = getJwtTokenUtil().generateToken(userDetails);
            getJwtTokenRepository().add(userDetails.getUsername(), token);
        }
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public JwtTokenUtil getJwtTokenUtil() {
        return jwtTokenUtil;
    }

    public JwtTokenRepository getJwtTokenRepository() {
        return jwtTokenRepository;
    }

    public UserService getUserService() {
        return userService;
    }

    public JwtUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
