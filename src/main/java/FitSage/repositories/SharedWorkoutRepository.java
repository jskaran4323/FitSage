package fitsage.repositories;
import fitsage.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
public interface SharedWorkoutRepository extends JpaRepository<SharedWorkout, UUID> {
    Optional<SharedWorkout> findByUserId(UUID userId);
}
