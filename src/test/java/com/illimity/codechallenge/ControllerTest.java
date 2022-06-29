package com.illimity.codechallenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collection;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    protected <T, O> O performAndExpectWithCollection(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, Class<? extends Collection> collection, Class<?> response, ResultMatcher... resultMatchers) {
        try {
            MvcResult mvcResult = performAndExpect(mockHttpServletRequestBuilder, resultMatchers);
            return parse(collection, response, mvcResult);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected <T, O> O performAndExpect(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, T request, Class<O> response, ResultMatcher... resultMatchers) {
        try {
            MvcResult mvcResult = performAndExpect(mockHttpServletRequestBuilder.content(objectMapper.writeValueAsString(request)), resultMatchers);
            return parse(response, mvcResult);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected <T, O> O performAndExpect(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, Class<O> response, ResultMatcher... resultMatchers) {
        try {
            MvcResult mvcResult = performAndExpect(mockHttpServletRequestBuilder, resultMatchers);
            return parse(response, mvcResult);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected MvcResult performAndExpect(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, ResultMatcher... resultMatchers) {
        try {
            ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            for (ResultMatcher resultMatcher: resultMatchers) {
                resultActions.andExpect(resultMatcher);
            }

            return resultActions.andReturn();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private <O> O parse(Class<O> response, MvcResult mvcResult) throws java.io.IOException {
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString, response);
    }

    private <O> O parse(Class<? extends Collection> collection, Class<?> response, MvcResult mvcResult) throws java.io.IOException {
        String contentAsString = mvcResult.getResponse().getContentAsString();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(collection, response);
        return objectMapper.readValue(contentAsString, listType);
    }

    protected static <T> T random(Class<T> randomClass) {
        return new EasyRandom().nextObject(randomClass);
    }

}