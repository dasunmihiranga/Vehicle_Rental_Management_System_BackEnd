package edu.icet.service.impl;

import edu.icet.dto.SignUpRequest;
import edu.icet.dto.User;
import edu.icet.entity.UserEntity;
import edu.icet.repository.UserRepository;
import edu.icet.service.AuthService;
import edu.icet.util.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        UserEntity adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount==null){
            UserEntity newAdminAccount=new UserEntity(
                    "Admin",
                    "admin@test.com",
                    new BCryptPasswordEncoder().encode("admin"),
                    UserRole.ADMIN
            );
            userRepository.save(newAdminAccount);
            System.out.println("Admin Account Successfully");
        }
    }

    @Override
    public User createCustomer(SignUpRequest signUpRequest) {
        UserEntity user=new UserEntity(
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                new BCryptPasswordEncoder().encode(signUpRequest.getPassword()),
                UserRole.CUSTOMER

        );

        UserEntity createdUser = userRepository.save(user);
        User userDto=new User();
        userDto.setId(createdUser.getId());

        return userDto;

    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
