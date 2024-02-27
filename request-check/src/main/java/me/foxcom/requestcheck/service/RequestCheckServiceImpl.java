package me.foxcom.requestcheck.service;

import lombok.RequiredArgsConstructor;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.mapper.RequestMapper;
import me.foxcom.requestresource.model.AskingResource;
import me.foxcom.requestresource.model.Request;
import me.foxcom.requestresource.model.enums.RequestStatus;
import me.foxcom.requestresource.model.enums.ResourceStatus;
import me.foxcom.requestresource.repository.RequestRepository;
import me.foxcom.requestresource.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestCheckServiceImpl implements RequestCheckService {

    private final RequestRepository requestRepository;
    private final ResourceRepository resourceRepository;
    private int flag = 0;

    @Override
    public List<RequestDto> startRequestCheck() {

        flag = 1;
        int count;

        List<Request> requestUnDoneList = requestRepository.findAllByStatusIsNull().stream()
                .sorted(Comparator.comparing(Request::getRequestDate))
                .collect(Collectors.toList());

        List<Request> doneRequests = requestRepository.findAllByStatusOrStatus(RequestStatus.APPROVED, RequestStatus.PARTLY_APPROVED);

        for (Request request : requestUnDoneList) {
            if (flag == 1) {

                List<AskingResource> askingResourceList = request.getAskingResourceList();
                List<AskingResource> doneAskingResource = new ArrayList<>();

                for (AskingResource askingResource : askingResourceList) {
                    if (askingResource.getResource().getQuota() >= askingResource.getCount()
                            && doneAskingResource.stream().filter(streamAskingResource -> streamAskingResource.getResource().getName().equals(askingResource.getResource().getName()))
                            .noneMatch(streamAskingResource -> streamAskingResource.getStatus().equals(ResourceStatus.APPROVED))
                            && doneRequests.stream().filter(streamRequest -> streamRequest.getName().equals(request.getName())
                                    && streamRequest.getSureName().equals(request.getSureName())
                                    && streamRequest.getPatronymic().equals(request.getPatronymic()))
                            .noneMatch(doneRequest -> doneRequest.getAskingResourceList().stream().filter(streamAskingResource -> streamAskingResource.getResource().getName().equals(askingResource.getResource().getName()))
                                    .anyMatch(streamAskingResource -> streamAskingResource.getStatus().equals(ResourceStatus.APPROVED)))
                            && !request.getRequestDate().isBefore(askingResource.getResource().getStartDate())
                            && !request.getRequestDate().isAfter(askingResource.getResource().getEndDate())) {

                        askingResource.setStatus(ResourceStatus.APPROVED);
                        askingResource.getResource().setQuota(askingResource.getResource().getQuota() - askingResource.getCount());
                        resourceRepository.save(askingResource.getResource());
                        doneAskingResource.add(askingResource);
                    } else {
                        askingResource.setStatus(ResourceStatus.REJECTED);
                        doneAskingResource.add(askingResource);
                    }
                }

                count = 0;

                for (AskingResource askingResource : askingResourceList) {
                    if (askingResource.getStatus().equals(ResourceStatus.APPROVED)) {
                        count += 1;
                    } else if (askingResource.getStatus().equals(ResourceStatus.REJECTED)) {
                        count -= 1;
                    }
                }

                if (count == askingResourceList.size()) {
                    request.setStatus(RequestStatus.APPROVED);
                } else if (count == -askingResourceList.size()) {
                    request.setStatus(RequestStatus.REJECTED);
                } else {
                    request.setStatus(RequestStatus.PARTLY_APPROVED);
                }

                doneRequests.add(request);

            } else {
                return requestRepository.saveAll(doneRequests).stream().map(RequestMapper::createRequestDto).collect(Collectors.toList());
            }
        }
        return requestRepository.saveAll(doneRequests).stream().map(RequestMapper::createRequestDto).collect(Collectors.toList());
    }

    @Override
    public void stopRequestCheck() {
        flag = 0;
    }
}
