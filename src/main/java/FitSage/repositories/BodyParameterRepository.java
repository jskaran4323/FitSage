package fitsage.repositories;

import fitsage.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface BodyParameterRepository extends JpaRepository<BodyParameter, UUID> {
    Optional<BodyParameter> findByUserId(UUID userId);
}


