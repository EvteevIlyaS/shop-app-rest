package com.ilyaevteev.shopapp.rest;

import com.ilyaevteev.shopapp.dto.auth.AuthenticationRequestDto;
import com.ilyaevteev.shopapp.dto.auth.RegistrationDto;
import com.ilyaevteev.shopapp.model.auth.User;
import com.ilyaevteev.shopapp.security.jwt.JwtTokenProvider;
import com.ilyaevteev.shopapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistrationDto requestDto) {
        String username = requestDto.getUsername();

        if (userService.findByUsername(username) != null
                | requestDto.getEmail() == null | requestDto.getPassword() == null
                | requestDto.getBalance() == null | username == null) {
            throw new BadCredentialsException("Invalid data");
        }

        User user = new User();

        user.setUsername(username);
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setBalance(requestDto.getBalance());

        userService.register(user);

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        return ResponseEntity.ok(response);
    }
}
