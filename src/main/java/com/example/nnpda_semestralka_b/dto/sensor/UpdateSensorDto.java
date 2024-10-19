package com.example.nnpda_semestralka_b.dto.sensor;

import lombok.Getter;
import lombok.Setter;

public class UpdateSensorDto {
    @Getter
    @Setter
    String deviceName;

    @Getter
    @Setter
    String sensorName;

    @Getter
    @Setter
    String newSensorName;
}
