package com.sambrannen.spring.events;

import java.util.Collections;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;
import com.sambrannen.spring.events.service.EventService;
import com.sambrannen.spring.events.service.StandardEventService;
import com.sambrannen.spring.events.web.EventsController;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringApplicationConfiguration(EventsControllerTests.TestConfiguration.class)
@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class EventsControllerTests {

    @ClassRule
    public static final SpringClassRule SPRING_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springRule = new SpringMethodRule();

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(wac).build();
    }

    @Test
    public void shouldDisplayRepositoryItemsInitially() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(view().name("event/list"))
            .andExpect(model().attribute("events", hasSize(1)));
    }

    @Configuration
    @EnableWebMvc
    static class TestConfiguration {

        @Bean
        public EventRepository eventRepository() {
            EventRepository repository = Mockito.mock(EventRepository.class);
            Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(new Event(1L)));
            return repository;
        }

        @Bean
        public EventService eventService() {
            return new StandardEventService(eventRepository());
        }

        @Bean
        public EventsController eventsController() {
            return new EventsController(eventService());
        }
    }
}
