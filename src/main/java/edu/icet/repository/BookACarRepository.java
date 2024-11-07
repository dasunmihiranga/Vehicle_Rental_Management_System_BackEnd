package edu.icet.repository;

import edu.icet.dto.BookACar;
import edu.icet.entity.BookACarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookACarRepository extends JpaRepository<BookACarEntity,Long> {
    List<BookACarEntity> findAllByUserId(Long userId);
}
