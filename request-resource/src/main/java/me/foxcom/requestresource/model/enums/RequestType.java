package me.foxcom.requestresource.model.enums;

import lombok.Getter;

@Getter
public enum RequestType {

    DRAWING_LOTS("жеребьевочные"),
    MASS_SPECIES("массовые виды");

    private final String displayName;

    RequestType(String displayName) {
        this.displayName = displayName;
    }

}
