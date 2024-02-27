package me.foxcom.requestresource.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.foxcom.requestresource.dto.AskingResourceDto;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.error.exception.EntityNotFoundException;
import me.foxcom.requestresource.model.enums.RequestType;
import me.foxcom.requestresource.service.RequestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static me.foxcom.requestresource.error.constants.ErrorStrings.REQUEST_NOT_FOUND_BY_ID;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestController.class)
class RequestControllerUnitTest {

    @MockBean
    RequestService requestService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mvc;
    private final LocalDate localDateNow = LocalDate.now();

    @Test
    void postRequest() throws Exception {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(1).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").count(1).build();
        RequestDto requestDto = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, LocalDate.now(), 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, null);

        when(requestService.postRequest(Mockito.any(RequestDto.class)))
                .thenReturn(requestDto);

        mvc.perform(post("/request")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(requestDto.getName())))
                .andExpect(jsonPath("$.sureName", is(requestDto.getSureName())))
                .andExpect(jsonPath("$.patronymic", is(requestDto.getPatronymic())))
                .andExpect(jsonPath("$.type", is(requestDto.getType().name())))
                .andExpect(jsonPath("$.issueDate", is(requestDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.series", is(requestDto.getSeries())))
                .andExpect(jsonPath("$.number", is(requestDto.getNumber())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].area", is(requestDto.getAskingResourceDtoList().get(0).getArea())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].name", is(requestDto.getAskingResourceDtoList().get(0).getName())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].count", is(requestDto.getAskingResourceDtoList().get(0).getCount())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].status", is(requestDto.getAskingResourceDtoList().get(0).getStatus())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].area", is(requestDto.getAskingResourceDtoList().get(1).getArea())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].name", is(requestDto.getAskingResourceDtoList().get(1).getName())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].count", is(requestDto.getAskingResourceDtoList().get(1).getCount())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].status", is(requestDto.getAskingResourceDtoList().get(1).getStatus())))
                .andExpect(jsonPath("$.status", is(requestDto.getStatus())));

    }

    @Test
    void getAllRequests() throws Exception {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(1).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").count(1).build();
        RequestDto requestDto = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, LocalDate.now(), 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, null);
        List<RequestDto> requestDtoList = List.of(requestDto);

        when(requestService.getAllRequests(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(List.of(requestDto));

        mvc.perform(get("/request")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(requestDtoList.get(0).getName())))
                .andExpect(jsonPath("$[0].sureName", is(requestDtoList.get(0).getSureName())))
                .andExpect(jsonPath("$[0].patronymic", is(requestDtoList.get(0).getPatronymic())))
                .andExpect(jsonPath("$[0].type", is(requestDtoList.get(0).getType().name())))
                .andExpect(jsonPath("$[0].issueDate", is(requestDtoList.get(0).getIssueDate().toString())))
                .andExpect(jsonPath("$[0].series", is(requestDtoList.get(0).getSeries())))
                .andExpect(jsonPath("$[0].number", is(requestDtoList.get(0).getNumber())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].area", is(requestDtoList.get(0).getAskingResourceDtoList().get(0).getArea())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].name", is(requestDtoList.get(0).getAskingResourceDtoList().get(0).getName())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].count", is(requestDtoList.get(0).getAskingResourceDtoList().get(0).getCount())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].status", is(requestDtoList.get(0).getAskingResourceDtoList().get(0).getStatus())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].area", is(requestDtoList.get(0).getAskingResourceDtoList().get(1).getArea())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].name", is(requestDtoList.get(0).getAskingResourceDtoList().get(1).getName())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].count", is(requestDtoList.get(0).getAskingResourceDtoList().get(1).getCount())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].status", is(requestDtoList.get(0).getAskingResourceDtoList().get(1).getStatus())))
                .andExpect(jsonPath("$[0].status", is(requestDtoList.get(0).getStatus())));

    }

    @Test
    void patchRequestThrowsEntityNotFoundException() throws Exception {

        when(requestService.getAllRequests(Mockito.anyInt(), Mockito.anyInt()))
                .thenThrow(new EntityNotFoundException(REQUEST_NOT_FOUND_BY_ID));

        mvc.perform(get("/request")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals(REQUEST_NOT_FOUND_BY_ID, Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void patchRequest() throws Exception {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(1).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").count(1).build();
        RequestDto requestDto = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, LocalDate.now(), 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, null);

        when(requestService.patchRequest(Mockito.any(RequestDto.class), Mockito.anyInt()))
                .thenReturn(requestDto);

        mvc.perform(patch("/request/{requestId}", 1)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(requestDto.getName())))
                .andExpect(jsonPath("$.sureName", is(requestDto.getSureName())))
                .andExpect(jsonPath("$.patronymic", is(requestDto.getPatronymic())))
                .andExpect(jsonPath("$.type", is(requestDto.getType().name())))
                .andExpect(jsonPath("$.issueDate", is(requestDto.getIssueDate().toString())))
                .andExpect(jsonPath("$.series", is(requestDto.getSeries())))
                .andExpect(jsonPath("$.number", is(requestDto.getNumber())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].area", is(requestDto.getAskingResourceDtoList().get(0).getArea())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].name", is(requestDto.getAskingResourceDtoList().get(0).getName())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].count", is(requestDto.getAskingResourceDtoList().get(0).getCount())))
                .andExpect(jsonPath("$.askingResourceDtoList.[0].status", is(requestDto.getAskingResourceDtoList().get(0).getStatus())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].area", is(requestDto.getAskingResourceDtoList().get(1).getArea())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].name", is(requestDto.getAskingResourceDtoList().get(1).getName())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].count", is(requestDto.getAskingResourceDtoList().get(1).getCount())))
                .andExpect(jsonPath("$.askingResourceDtoList.[1].status", is(requestDto.getAskingResourceDtoList().get(1).getStatus())))
                .andExpect(jsonPath("$.status", is(requestDto.getStatus())));

    }

    @Test
    void deleteRequest() throws Exception {

        mvc.perform(delete("/request/{id}", 1)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        Mockito.verify(requestService, Mockito.times(1))
                .deleteRequest(1);
    }

}