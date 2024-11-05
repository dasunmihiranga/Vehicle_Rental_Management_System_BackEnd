package edu.icet.repository;

import edu.icet.entity.UserEntity;
import edu.icet.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findFirstByEmail(String email);

    UserEntity findByUserRole(UserRole userRole);
}
