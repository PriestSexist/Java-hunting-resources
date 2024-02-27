package me.foxcom.requestcheck.service;

import me.foxcom.requestresource.dto.AskingResourceDto;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.model.AskingResource;
import me.foxcom.requestresource.model.Request;
import me.foxcom.requestresource.model.Resource;
import me.foxcom.requestresource.model.enums.RequestStatus;
import me.foxcom.requestresource.model.enums.RequestType;
import me.foxcom.requestresource.model.enums.ResourceStatus;
import me.foxcom.requestresource.repository.RequestRepository;
import me.foxcom.requestresource.repository.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestCheckServiceImplUnitTest {

    @Mock
    RequestRepository requestRepository;
    @Mock
    ResourceRepository resourceRepository;
    RequestCheckService requestCheckService;
    private final LocalDate localDateNow = LocalDate.now();

    @BeforeEach
    void generator() {
        requestCheckService = new RequestCheckServiceImpl(requestRepository, resourceRepository);
    }

    @Test
    void startRequestCheck() throws Exception {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").status(ResourceStatus.APPROVED).count(5).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").status(ResourceStatus.APPROVED).count(5).build();

        Resource resource1 = Resource.builder().id(1).area("Russia").name("Fish").quota(10).startDate(localDateNow.minusDays(10)).endDate(localDateNow.plusDays(10)).build();
        Resource resource2 = Resource.builder().id(2).area("Russia").name("Wolf").quota(10).startDate(localDateNow.minusDays(5)).endDate(localDateNow.plusDays(15)).build();

        AskingResource askingResource1 = AskingResource.builder().resource(resource1).status(ResourceStatus.APPROVED).count(5).build();
        AskingResource askingResource2 = AskingResource.builder().resource(resource2).status(ResourceStatus.APPROVED).count(5).build();

        RequestDto requestDtoBeforeWork = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, RequestStatus.APPROVED);
        Request request = new Request(1, "Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResource1, askingResource2), null, localDateNow);

        askingResource1.setRequest(request);
        askingResource2.setRequest(request);

        List<RequestDto> requestDtoListBeforeWork = List.of(requestDtoBeforeWork);
        List<Request> requestList = List.of(request);

        when(requestRepository.saveAll(Mockito.anyList()))
                .thenReturn(requestList);

        Mockito.when(requestRepository.findAllByStatusIsNull())
                .thenReturn(List.of(request));

        Mockito.when(requestRepository.findAllByStatusOrStatus(Mockito.any(RequestStatus.class), Mockito.any(RequestStatus.class)))
                .thenReturn(new ArrayList<>());

        Mockito.when(resourceRepository.save(Mockito.any(Resource.class)))
                .thenReturn(resource1);

        List<RequestDto> requestDtoListAfterWork = requestCheckService.startRequestCheck();

        Assertions.assertEquals(requestDtoListAfterWork, requestDtoListBeforeWork);
    }
}