package mariana.springbackend.repositories;

import mariana.springbackend.entities.ExposureEntity;
import mariana.springbackend.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long> {
    ExposureEntity findById(long id);
}
