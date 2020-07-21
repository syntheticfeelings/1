package project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.domain.Actor;
import project.dto.ActorCreateDTO;
import project.dto.ActorReadDTO;
import project.exception.EntityNotFoundException;
import project.service.ActorService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ActorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ActorService actorService;

    @Test
    public void testGetActor() throws Exception {
        ActorReadDTO actor = new ActorReadDTO();
        actor.setId(UUID.randomUUID());
        actor.setName("Matthew");
        actor.setSurName("McConaughey");

        Mockito.when(actorService.getActor(actor.getId())).thenReturn(actor);

        String resultJson = mvc.perform(get("/api/v1/actors/{id}", actor.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(resultJson);
        ActorReadDTO actualActor = objectMapper.readValue(resultJson, ActorReadDTO.class);
        Assertions.assertThat(actualActor).isEqualToComparingFieldByField(actor);

        Mockito.verify(actorService).getActor(actor.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetActorWrongId() throws Exception {
        UUID wrongID = UUID.fromString("123");

        EntityNotFoundException exception = new EntityNotFoundException(Actor.class, wrongID);
        Mockito.when(actorService.getActor(wrongID)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/actors/{id}", wrongID))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue((resultJson.contains(exception.getMessage())));
    }


    @Test
    public void testCreateActor() throws Exception {
        ActorCreateDTO create = new ActorCreateDTO();
        create.setName("Bob");
        create.setSurName("Dylan");

        ActorReadDTO read = new ActorReadDTO();
        read.setId(UUID.randomUUID());
        read.setName("Bob");
        read.setSurName("Dylan");

        Mockito.when(actorService.createActor(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/actors")
            .content(objectMapper.writeValueAsString(create))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        ActorReadDTO actualActor = objectMapper.readValue(resultJson, ActorReadDTO.class);
        Assertions.assertThat(actualActor).isEqualToComparingFieldByField(read);

    }

}
