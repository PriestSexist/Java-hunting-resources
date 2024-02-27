package me.foxcom.requestresource.service;

import me.foxcom.requestresource.dto.AskingResourceDto;
import me.foxcom.requestresource.dto.RequestDto;
import me.foxcom.requestresource.error.exception.EntityNotFoundException;
import me.foxcom.requestresource.model.AskingResource;
import me.foxcom.requestresource.model.Request;
import me.foxcom.requestresource.model.Resource;
import me.foxcom.requestresource.model.enums.RequestType;
import me.foxcom.requestresource.repository.RequestRepository;
import me.foxcom.requestresource.repository.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplUnitTest {

    @Mock
    RequestRepository requestRepository;
    @Mock
    ResourceRepository resourceRepository;
    RequestService requestService;
    private final LocalDate localDateNow = LocalDate.now();

    @BeforeEach
    void generator() {
        requestService = new RequestServiceImpl(requestRepository, resourceRepository);
    }

    @Test
    void postRequest() {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(10).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").count(20).build();

        Resource resource1 = Resource.builder().id(1).area("Russia").name("Fish").quota(100).startDate(localDateNow.minusDays(10)).endDate(localDateNow.plusDays(10)).build();
        Resource resource2 = Resource.builder().id(2).area("Russia").name("Wolf").quota(10).startDate(localDateNow.minusDays(5)).endDate(localDateNow.plusDays(15)).build();

        AskingResource askingResource1 = AskingResource.builder().resource(resource1).count(10).build();
        AskingResource askingResource2 = AskingResource.builder().resource(resource2).count(20).build();

        RequestDto requestDtoBeforeWork = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, null);
        Request request = new Request(1, "Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResource1, askingResource2), null, localDateNow);

        askingResource1.setRequest(request);
        askingResource2.setRequest(request);

        Mockito.when(requestRepository.save(Mockito.any(Request.class)))
                .thenReturn(request);

        RequestDto requestDtoAfterWork = requestService.postRequest(requestDtoBeforeWork);

        Assertions.assertEquals(requestDtoBeforeWork, requestDtoAfterWork);
    }

    @Test
    void getAllRequests() {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(10).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").count(20).build();

        Resource resource1 = Resource.builder().id(1).area("Russia").name("Fish").quota(100).startDate(localDateNow.minusDays(10)).endDate(localDateNow.plusDays(10)).build();
        Resource resource2 = Resource.builder().id(2).area("Russia").name("Wolf").quota(10).startDate(localDateNow.minusDays(5)).endDate(localDateNow.plusDays(15)).build();

        AskingResource askingResource1 = AskingResource.builder().resource(resource1).count(10).build();
        AskingResource askingResource2 = AskingResource.builder().resource(resource2).count(20).build();

        RequestDto requestDtoBeforeWork = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResourceDto1, askingResourceDto2), localDateNow, null);
        Request request = new Request(1, "Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResource1, askingResource2), null, localDateNow);

        askingResource1.setRequest(request);
        askingResource2.setRequest(request);

        Collection<RequestDto> requestDtoListBeforeWork = List.of(requestDtoBeforeWork);
        List<Request> requestList = List.of(request);

        Page<Request> itemsPage = new PageImpl<>(requestList);

        Mockito.when(requestRepository.findAll(Mockito.any(PageRequest.class)))
                .thenReturn(itemsPage);

        Collection<RequestDto> requestDtoListAfterWork = requestService.getAllRequests(0, 10);

        Assertions.assertEquals(requestDtoListBeforeWork, requestDtoListAfterWork);
    }

    @Test
    void patchRequest() {
        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(10).build();
        AskingResourceDto askingResourceDto2 = AskingResourceDto.builder().area("Russia").name("Wolf").count(20).build();

        Resource resource1 = Resource.builder().id(1).area("Russia").name("Fish").quota(100).startDate(localDateNow.minusDays(10)).endDate(localDateNow.plusDays(10)).build();
        Resource resource2 = Resource.builder().id(2).area("Russia").name("Wolf").quota(10).startDate(localDateNow.minusDays(5)).endDate(localDateNow.plusDays(15)).build();

        AskingResource askingResource1 = AskingResource.builder().resource(resource1).count(10).build();
        AskingResource askingResource2 = AskingResource.builder().resource(resource2).count(20).build();

        Request requestBeforeWork = new Request(1, "Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResource1), null, localDateNow);
        Request requestAfterWork = new Request(1, "Biktor", "V", "Evgenevichch", RequestType.MASS_SPECIES, localDateNow.plusDays(2), 20, 200000, List.of(askingResource2), null, localDateNow);

        RequestDto requestDtoAfterWork = new RequestDto("Biktor", "V", "Evgenevichch", RequestType.MASS_SPECIES, localDateNow.plusDays(2), 20, 200000, List.of(askingResourceDto2), localDateNow, null);

        askingResource1.setRequest(requestBeforeWork);
        askingResource2.setRequest(requestAfterWork);

        Mockito.when(requestRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(requestBeforeWork));

        Mockito.when(requestRepository.save(Mockito.any(Request.class)))
                .thenReturn(requestAfterWork);

        RequestDto requestDto = requestService.patchRequest(requestDtoAfterWork, 1);

        Assertions.assertEquals(requestDtoAfterWork, requestDto);
    }

    @Test
    void patchRequestThrowsEntityNotFoundException() {

        AskingResourceDto askingResourceDto1 = AskingResourceDto.builder().area("Russia").name("Fish").count(10).build();

        Resource resource1 = Resource.builder().id(1).area("Russia").name("Fish").quota(100).startDate(localDateNow.minusDays(10)).endDate(localDateNow.plusDays(10)).build();
        AskingResource askingResource1 = AskingResource.builder().resource(resource1).count(10).build();
        Request requestBeforeWork = new Request(1, "Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResource1), null, localDateNow);

        RequestDto requestDtoBeforeWork = new RequestDto("Viktor", "B", "Evgenevich", RequestType.DRAWING_LOTS, localDateNow, 10, 100000, List.of(askingResourceDto1), localDateNow, null);

        askingResource1.setRequest(requestBeforeWork);

        Assertions.assertThrows(EntityNotFoundException.class, () -> requestService.patchRequest(requestDtoBeforeWork, 1));
    }

    @Test
    void deleteRequest() {

        Mockito.when(requestRepository.existsById(Mockito.anyInt()))
                .thenReturn(true);

        requestService.deleteRequest(1);

        Mockito.verify(requestRepository, Mockito.times(1))
                .deleteById(1);
    }

    @Test
    void deleteRequestThrowsEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> requestService.deleteRequest(1));
    }
}