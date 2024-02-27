package me.foxcom.requestresource.dto;

import lombok.Builder;
import lombok.Data;
import me.foxcom.requestresource.model.enums.ResourceStatus;

@Data
@Builder
public class AskingResourceDto {
    private final String area;
    private final String name;
    private final int count;
    private ResourceStatus status;
}
