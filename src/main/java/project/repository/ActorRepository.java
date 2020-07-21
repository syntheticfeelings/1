package project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import project.domain.Actor;

import java.util.UUID;


@Repository
public interface ActorRepository extends CrudRepository<Actor, UUID> {

}
