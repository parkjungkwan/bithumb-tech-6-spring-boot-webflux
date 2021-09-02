package net.zerotodev.api.quiz.controller;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import net.zerotodev.api.quiz.cofing.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.zerotodev.api.quiz.domain.Attempt;
import net.zerotodev.api.quiz.domain.Quiz;
import net.zerotodev.api.quiz.domain.User;
import net.zerotodev.api.quiz.service.QuizService;
import static net.zerotodev.api.quiz.controller.AttemptController.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttemptController.class)
class AttemptControllerTest {
    @Autowired MockMvc mvc;
    @MockBean QuizService quizService;
    private JacksonTester<Attempt> jsonResult;
    private JacksonTester<ResultResponse> jsonResponse;
    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void postResult() throws Exception{
        genericParameterizedTest(true);
    }

    private void genericParameterizedTest(final boolean correct) throws Exception {
        given(quizService.checkAttempt(any(Attempt.class))).willReturn(correct);
        User user = new User("john","happy-john","john","john@test.com");
        Quiz quiz = new Quiz(50,70);
        Attempt attempt = new Attempt(user, quiz, 3500, correct);
        assertThat(attempt.getUser().getAlias(), is(equalTo("happy-john")));

        /**
        MockHttpServletResponse response = mvc.perform(post("/attempts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResult.write(attempt).getJson())).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonResult
                        .write(new Attempt(
                                attempt.getUser(),
                                attempt.getQuiz(),
                                attempt.getResultAttempt(),
                                correct)).getJson());
        */
    }

    @Test
    void getStatistics() throws Exception{
    }

    @Test
    void getResultById() throws Exception{
    }
}