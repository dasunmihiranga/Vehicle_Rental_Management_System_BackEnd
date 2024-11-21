package edu.icet.controller;

import edu.icet.dto.AuthenticationRequest;
import edu.icet.dto.AuthenticationResponse;
import edu.icet.dto.SignUpRequest;
import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserRepository;
import edu.icet.service.AuthService;
import edu.icet.service.EmailService;
import edu.icet.service.UserService;
import edu.icet.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin

public class AuthController{
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    final private EmailService emailService;


    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignUpRequest signUpRequest){
        if (authService.hasCustomerWithEmail(signUpRequest.getEmail()))
            return new ResponseEntity<>("Customer already exist with this email",HttpStatus.NOT_ACCEPTABLE);
        User createdCustomer = authService.createCustomer(signUpRequest);
        if (null==createdCustomer)return new ResponseEntity<>("Customer not created,Come again later", HttpStatus.BAD_REQUEST);
        try {
            emailService.sendAccountCreatedEmail(signUpRequest.getEmail(), signUpRequest.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(createdCustomer,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)throws
            BadCredentialsException,
            DisabledException,
            UsernameNotFoundException{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password.");
        }
        final UserDetails userDetails =userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<UserEntity> optionalUser =userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt =jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse =new AuthenticationResponse();
        if (optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return  authenticationResponse;
    }

}
