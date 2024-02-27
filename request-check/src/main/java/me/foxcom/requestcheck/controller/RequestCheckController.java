package me.foxcom.requestcheck.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.foxcom.requestcheck.service.RequestCheckService;
import me.foxcom.requestresource.dto.RequestDto;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/check")
public class RequestCheckController {

    private final RequestCheckService requestCheckService;

    @PatchMapping("/start")
    public List<RequestDto> startRequestCheck() {
        log.debug("Вызван метод postRequest");
        return requestCheckService.startRequestCheck();
    }

    @PatchMapping("/stop")
    public void stopRequestCheck() {
        log.debug("Вызван метод stopRequestCheck");
        requestCheckService.stopRequestCheck();
    }
}
