package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.domain.Actor;
import project.dto.ActorCreateDTO;
import project.dto.ActorReadDTO;
import project.service.ActorService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("/{id}")
    public ActorReadDTO getActor(@PathVariable UUID id) {
        return actorService.getActor(id);
    }

    @PostMapping
    public ActorReadDTO createActor(@RequestBody ActorCreateDTO createDTO) {
        return actorService.createActor(createDTO);
    }

}
