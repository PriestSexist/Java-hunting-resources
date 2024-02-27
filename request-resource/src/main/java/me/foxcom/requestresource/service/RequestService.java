package me.foxcom.requestresource.service;

import me.foxcom.requestresource.dto.RequestDto;

import java.util.List;

public interface RequestService {
    RequestDto postRequest(RequestDto requestDto);

    List<RequestDto> getAllRequests(int from, int size);

    RequestDto patchRequest(RequestDto requestDto, int requestId);

    void deleteRequest(int requestId);
}
