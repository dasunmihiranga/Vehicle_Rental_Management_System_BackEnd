package edu.icet.repository;

import edu.icet.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository  extends JpaRepository<CarEntity,Long> {
}
