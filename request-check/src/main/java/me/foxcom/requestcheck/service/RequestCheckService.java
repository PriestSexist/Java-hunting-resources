package me.foxcom.requestcheck.service;

import me.foxcom.requestresource.dto.RequestDto;

import java.util.List;

public interface RequestCheckService {
    List<RequestDto> startRequestCheck();

    void stopRequestCheck();
}
