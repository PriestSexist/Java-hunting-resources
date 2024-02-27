package me.foxcom.requestresource.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.foxcom.requestresource.model.enums.RequestStatus;
import me.foxcom.requestresource.model.enums.RequestType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class RequestDto {
    private final String name;
    private final String sureName;
    private final String patronymic;
    private final RequestType type;
    private final LocalDate issueDate;
    private final Integer series;
    private final Integer number;
    private final List<AskingResourceDto> askingResourceDtoList;
    private final LocalDate requestDate;
    private final RequestStatus status;
}
