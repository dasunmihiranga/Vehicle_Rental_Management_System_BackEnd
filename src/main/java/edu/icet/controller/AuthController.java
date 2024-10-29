package edu.icet.controller;

import edu.icet.dto.SignUpRequest;
import edu.icet.dto.User;
import edu.icet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignUpRequest signUpRequest){
        if (authService.hasCustomerWithEmail(signUpRequest.getEmail()))
            return new ResponseEntity<>("Customer already exist with this email",HttpStatus.NOT_ACCEPTABLE);
        User createdCustomer = authService.createCustomer(signUpRequest);
        if (null==createdCustomer)return new ResponseEntity<>("Customer not created,Come again later", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(createdCustomer,HttpStatus.CREATED);
    }

}
