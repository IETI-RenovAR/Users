package org.adaschool.project.controller.auth;

import org.adaschool.project.dto.LoginDTO;
import org.adaschool.project.dto.TokenDTO;
import org.adaschool.project.exception.InvalidCredentialsException;
import org.adaschool.project.model.UserEntity;
import org.adaschool.project.security.JwtUtil;
import org.adaschool.project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final Object ERROR_KEY = "error";

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/public")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.ok("El endpoint funciona.");
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto) {
        Optional<UserEntity> optionalUser = userService.getUserByEmail(loginDto.getEmail());
        if(optionalUser.isPresent()){
            UserEntity userEntity = optionalUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(), userEntity.getPasswordHash())){
                TokenDTO tokenDto = jwtUtil.generateToken(userEntity.getEmail(), userEntity.getRoles());
                return ResponseEntity.ok(tokenDto);
            }
            else{
                return new ResponseEntity<>(Map.of(ERROR_KEY, InvalidCredentialsException.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
            }
        }
        else{
            return new ResponseEntity<>(Map.of(ERROR_KEY, InvalidCredentialsException.INVALID_CREDENTIALS), HttpStatus.UNAUTHORIZED);
        }
    }
}