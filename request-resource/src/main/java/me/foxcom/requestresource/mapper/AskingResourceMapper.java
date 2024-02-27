package me.foxcom.requestresource.mapper;

import lombok.experimental.UtilityClass;
import me.foxcom.requestresource.dto.AskingResourceDto;
import me.foxcom.requestresource.model.AskingResource;
import me.foxcom.requestresource.repository.ResourceRepository;

@UtilityClass
public class AskingResourceMapper {
    public static AskingResource createAskingResource(AskingResourceDto askingResourceDto, ResourceRepository resourceRepository) {
        return AskingResource.builder()
                .count(askingResourceDto.getCount())
                .status(askingResourceDto.getStatus())
                .resource(resourceRepository.getResourceByAreaAndName(askingResourceDto.getArea(), askingResourceDto.getName()))
                .build();
    }

    public static AskingResourceDto createAskingResourceDto(AskingResource askingResource) {
        return AskingResourceDto.builder()
                .area(askingResource.getResource().getArea())
                .name(askingResource.getResource().getName())
                .count(askingResource.getCount())
                .status(askingResource.getStatus())
                .build();
    }
}
