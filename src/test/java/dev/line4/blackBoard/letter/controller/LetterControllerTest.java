package dev.line4.blackBoard.letter.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LetterControllerTest {

    @Autowired
    MockMvc mvc;

    final String blackboard = "{"
            + "\"blackboard\" : {"
            + "    \"title\" : \"제목제목\","
            + "    \"introduction\" : \"소개소개\","
            + "    \"userId\" : \"this-is-test\","
            + "    \"openDate\" : \"2024-02-20T18:00:00\""
            + "},"
            + "\"stickers\" : ["
            + "]"
            + "}";

    final String letter1 = "{"
            + "\"letter\" : {"
            + "    \"nickname\":\"test1\","
            + "    \"content\":\"내용입니다\","
            + "    \"font\":\"Alien\","
            + "    \"align\":\"center\""
            + " },"
            + "\"stickers\":["
            + " ]"
            + "}";

    final String letter2 = "{"
            + "\"letter\" : {"
            + "    \"nickname\":\"test2\","
            + "    \"content\":\"내용입니다\","
            + "    \"font\":\"Alien\","
            + "    \"align\":\"center\""
            + " },"
            + "\"stickers\":["
            + " ]"
            + "}";

    @Test
    @Transactional
    @DisplayName("편지 작성자 목록 조회 - 성공(0명)")
    void getLetterWritersTestSuccess0() throws Exception {

        // 칠판 등록
        registerBlackboard();

        // 응답 확인 - 편지 등록자가 0명이어야 함
        mvc.perform(MockMvcRequestBuilders.get("/api/writer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "this-is-test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.writers.length()").value(0));
    }

    @Test
    @Transactional
    @DisplayName("편지 작성자 목록 조회 - 성공(2명)")
    void getLetterWritersTestSuccess2() throws Exception {

        // 칠판 등록
        registerBlackboard();

        // 편지 등록 1 - test1
        registerLetter(letter1, "this-is-test");

        // 편지 등록 2 - test2
        registerLetter(letter2, "this-is-test");

        // 응답 확인 - 편지 등록자가 2명이어야 함
        mvc.perform(MockMvcRequestBuilders.get("/api/writer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "this-is-test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.writers.length()").value(2));
    }

    @Test
    @DisplayName("편지 작성자 조회 - 실패")
    @Transactional
    void getLetterWritersTestFail() throws Exception {

        // 존재하지 않는 칠판으로 요청을 보낸 경우 404
        mvc.perform(MockMvcRequestBuilders.get("/api/writer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "never-existssssss"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("편지 작성 - 성공")
    @Transactional
    void addLetterSuccess() throws Exception {

        // 칠판 등록
        registerBlackboard();

        // 편지 등록
        mvc.perform(MockMvcRequestBuilders.post("/api/letter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "this-is-test")
                        .content(letter1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("편지 작성 - 실패")
    @Transactional
    void addLetterFail() throws Exception {

        // 편지 등록 - 등록되지 않은 칠판에 편지를 작성하므로 404
        mvc.perform(MockMvcRequestBuilders.post("/api/letter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", "tdkfjekwjkjeksjaljsdk")
                        .content(letter1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // 공통된 테스트 작업을 위한 메소드
    private void registerBlackboard() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/blackboard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blackboard))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void registerLetter(String letter, String userId) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/letter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userId", userId)
                        .content(letter))
                .andDo(print())
                .andExpect(status().isOk());
    }

}