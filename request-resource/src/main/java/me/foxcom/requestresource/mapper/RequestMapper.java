package me.foxcom.requestresource.mapper;

import lombok.experimental.UtilityClass;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.model.Request;
import me.foxcom.requestresource.repository.ResourceRepository;

import java.time.LocalDate;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {
    public static Request createRequest(RequestDto requestDto, ResourceRepository resourceRepository) {
        return Request.builder()
                .name(requestDto.getName())
                .sureName(requestDto.getSureName())
                .patronymic(requestDto.getPatronymic())
                .type(requestDto.getType())
                .issueDate(requestDto.getIssueDate())
                .series(requestDto.getSeries())
                .number(requestDto.getNumber())
                .askingResourceList(requestDto.getAskingResourceDtoList().stream()
                        .map(askingResourceDto -> AskingResourceMapper.createAskingResource(askingResourceDto, resourceRepository))
                        .collect(Collectors.toList()))
                .requestDate(LocalDate.now())
                .status(requestDto.getStatus())
                .build();
    }

    public static RequestDto createRequestDto(Request request) {
        return RequestDto.builder()
                .name(request.getName())
                .sureName(request.getSureName())
                .patronymic(request.getPatronymic())
                .type(request.getType())
                .issueDate(request.getIssueDate())
                .series(request.getSeries())
                .number(request.getNumber())
                .askingResourceDtoList(request.getAskingResourceList()
                        .stream()
                        .map(AskingResourceMapper::createAskingResourceDto)
                        .collect(Collectors.toList()))
                .status(request.getStatus())
                .requestDate(request.getRequestDate())
                .build();
    }
}
