package me.foxcom.requestresource.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto postRequest(@RequestBody @Valid RequestDto requestDto) {
        log.debug("Вызван метод postRequest");
        return requestService.postRequest(requestDto);
    }

    @GetMapping
    public List<RequestDto> getAllRequests(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод getAllRequests");
        return requestService.getAllRequests(from, size);
    }

    @PatchMapping("/{requestId}")
    public RequestDto patchRequest(@RequestBody RequestDto requestDto,
                                   @PathVariable int requestId) {
        log.debug("Вызван метод patchRequest");
        return requestService.patchRequest(requestDto, requestId);
    }

    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequest(@PathVariable @PositiveOrZero int requestId) {
        log.debug("Вызван метод deleteRequest");
        requestService.deleteRequest(requestId);
    }


}
