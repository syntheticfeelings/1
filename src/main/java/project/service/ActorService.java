package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.domain.Actor;
import project.dto.ActorCreateDTO;
import project.dto.ActorReadDTO;
import project.exception.EntityNotFoundException;
import project.repository.ActorRepository;

import java.util.UUID;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public ActorReadDTO getActor(UUID id) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(Actor.class, id);
        });
        return toRead(actor);
    }

    private ActorReadDTO toRead(Actor actor) {
        ActorReadDTO dto = new ActorReadDTO();
        dto.setId(actor.getId());
        dto.setName(actor.getName());
        dto.setSurName(actor.getSurName());
        return dto;
    }

    public ActorReadDTO createActor(ActorCreateDTO create) {
        Actor actor = new Actor();
        actor.setName(create.getName());
        actor.setSurName(create.getSurName());

        actor = actorRepository.save(actor);
        return toRead(actor);
    }

}
