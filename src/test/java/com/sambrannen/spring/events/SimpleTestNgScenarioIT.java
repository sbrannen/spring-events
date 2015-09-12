package com.sambrannen.spring.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.google.inject.Inject;
import com.sambrannen.spring.events.domain.Event;
import com.sambrannen.spring.events.repository.EventRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class SimpleTestNgScenarioIT extends AbstractTestNGSpringContextTests {

    private MockMvc mockMvc;

    @Autowired
    private EventRepository repository;

    @BeforeClass
    protected void setUpBeforeClass() {
        DefaultMockMvcBuilder mockMvcBuilder= webAppContextSetup((WebApplicationContext) applicationContext);
        mockMvc = mockMvcBuilder.build();
    }

    @Test
    public void shouldDisplayNineItemsInitially() throws Exception {
        MockHttpServletRequestBuilder getList = get("/");
        mockMvc.perform(getList)
            .andExpect(view().name("event/list"))
            .andExpect(model().attribute("events", hasSize(9)));
    }

    @Test(dependsOnMethods = "shouldDisplayNineItemsInitially")
    public void shouldDisplayAddForm() throws Exception {
        MockHttpServletRequestBuilder getList = get("/form");
        mockMvc.perform(getList)
            .andExpect(view().name("event/form"))
            .andExpect(model().attribute("event", isA(Event.class)));
    }

    @Test(dependsOnMethods = "shouldDisplayAddForm")
    public void shouldAddNewEvent() throws Exception {
        DefaultMockMvcBuilder mockMvcBuilder= webAppContextSetup((WebApplicationContext) applicationContext);
        MockMvc mockMvc = mockMvcBuilder.build();
        MockHttpServletRequestBuilder postForm = post("/form").param("name", "foobar").param("location", "bazqux");
        mockMvc.perform(postForm)
            .andExpect(view().name(startsWith("redirect:")));
        assertThat(repository.count()).isEqualTo(10);
    }

    @Test(dependsOnMethods = "shouldAddNewEvent")
    public void shouldDisplayTenItemsInTheEnd() throws Exception {
        MockHttpServletRequestBuilder getList = get("/");
        mockMvc.perform(getList)
            .andExpect(view().name("event/list"))
            .andExpect(model().attribute("events", hasSize(10)));
    }
}
