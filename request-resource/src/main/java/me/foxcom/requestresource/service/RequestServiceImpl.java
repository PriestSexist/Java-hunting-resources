package me.foxcom.requestresource.service;

import lombok.RequiredArgsConstructor;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.error.exception.EntityNotFoundException;
import me.foxcom.requestresource.mapper.AskingResourceMapper;
import me.foxcom.requestresource.mapper.RequestMapper;
import me.foxcom.requestresource.model.AskingResource;
import me.foxcom.requestresource.model.Request;
import me.foxcom.requestresource.repository.RequestRepository;
import me.foxcom.requestresource.repository.ResourceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static me.foxcom.requestresource.error.constants.ErrorStrings.REQUEST_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final ResourceRepository resourceRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RequestDto postRequest(RequestDto requestDto) {

        Request request = RequestMapper.createRequest(requestDto, resourceRepository);

        for (AskingResource askingResource : request.getAskingResourceList()) {
            askingResource.setRequest(request);
        }

        return RequestMapper.createRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<RequestDto> getAllRequests(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        return requestRepository.findAll(pageRequest).map(RequestMapper::createRequestDto).getContent();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RequestDto patchRequest(RequestDto requestDto, int requestId) {

        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityNotFoundException(REQUEST_NOT_FOUND_BY_ID));

        if (!requestDto.getName().isBlank()) {
            request.setName(requestDto.getName());
        }

        if (!requestDto.getSureName().isBlank()) {
            request.setSureName(requestDto.getSureName());
        }

        if (!requestDto.getPatronymic().isBlank()) {
            request.setPatronymic(requestDto.getPatronymic());
        }

        if (requestDto.getType() != null) {
            request.setType(requestDto.getType());
        }

        if (requestDto.getIssueDate() != null) {
            request.setIssueDate(requestDto.getIssueDate());
        }

        if (requestDto.getSeries() != null) {
            request.setSeries(requestDto.getSeries());
        }

        if (requestDto.getNumber() != null) {
            request.setNumber(requestDto.getNumber());
        }

        if (requestDto.getAskingResourceDtoList() != null) {
            request.setAskingResourceList(requestDto.getAskingResourceDtoList().stream()
                    .map(askingResourceDto -> AskingResourceMapper.createAskingResource(askingResourceDto, resourceRepository))
                    .collect(Collectors.toList()));
        }

        if (requestDto.getStatus() != null) {
            request.setStatus(requestDto.getStatus());
        }

        return RequestMapper.createRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRequest(int requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new EntityNotFoundException(REQUEST_NOT_FOUND_BY_ID);
        }
        requestRepository.deleteById(requestId);
    }


}
