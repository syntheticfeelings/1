package project.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import project.domain.Actor;
import project.dto.ActorCreateDTO;
import project.dto.ActorReadDTO;
import project.exception.EntityNotFoundException;
import project.repository.ActorRepository;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from actor", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ActorServiceTest {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ActorService actorService;

    @Test
    public void testGetActor() {
        Actor actor = new Actor();
        actor.setName("Matthew");
        actor.setSurName("McConaughey");
        actor = actorRepository.save(actor);

        ActorReadDTO readDTO = actorService.getActor(actor.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(actor);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetActorWrongId() {
        actorService.getActor(UUID.randomUUID());
    }

    @Test
    public void testCreateActor() {
        ActorCreateDTO  create = new ActorCreateDTO();
        create.setName("Matthew");
        create.setSurName("McConaughey");
        ActorReadDTO read = actorService.createActor(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Actor actor = actorRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToComparingFieldByField(actor);
    }

}
