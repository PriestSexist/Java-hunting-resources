package me.foxcom.requestcheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.foxcom.requestcheck.service.RequestCheckService;
import me.foxcom.requestresource.dto.AskingResourceDto;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.model.enums.RequestStatus;
import me.foxcom.requestresource.model.enums.RequestType;
import me.foxcom.requestresource.model.enums.ResourceStatus;
import me.foxcom.requestresource.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RequestCheckController.class)
class RequestCheckControllerUnitTest {

    @MockBean
    RequestCheckService requestCheckService;
    @MockBean
    RequestService requestService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mvc;
    private final LocalDate localDateNow = LocalDate.now();

    @Test
    void startRequestCheck() throws Exception {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").status(ResourceStatus.APPROVED).count(1).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").status(ResourceStatus.APPROVED).count(1).build();
        RequestDto requestDto = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, LocalDate.now(), 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, RequestStatus.APPROVED);
        List<RequestDto> requestDtoList = List.of(requestDto);
        when(requestCheckService.startRequestCheck())
                .thenReturn(requestDtoList);

        mvc.perform(patch("/check/start")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(requestDto.getName())))
                .andExpect(jsonPath("$[0].sureName", is(requestDto.getSureName())))
                .andExpect(jsonPath("$[0].patronymic", is(requestDto.getPatronymic())))
                .andExpect(jsonPath("$[0].type", is(requestDto.getType().name())))
                .andExpect(jsonPath("$[0].issueDate", is(requestDto.getIssueDate().toString())))
                .andExpect(jsonPath("$[0].series", is(requestDto.getSeries())))
                .andExpect(jsonPath("$[0].number", is(requestDto.getNumber())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].area", is(requestDto.getAskingResourceDtoList().get(0).getArea())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].name", is(requestDto.getAskingResourceDtoList().get(0).getName())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].count", is(requestDto.getAskingResourceDtoList().get(0).getCount())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[0].status", is(requestDto.getAskingResourceDtoList().get(0).getStatus().name())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].area", is(requestDto.getAskingResourceDtoList().get(1).getArea())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].name", is(requestDto.getAskingResourceDtoList().get(1).getName())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].count", is(requestDto.getAskingResourceDtoList().get(1).getCount())))
                .andExpect(jsonPath("$[0].askingResourceDtoList.[1].status", is(requestDto.getAskingResourceDtoList().get(1).getStatus().name())))
                .andExpect(jsonPath("$[0].status", is(requestDto.getStatus().name())));

    }
}