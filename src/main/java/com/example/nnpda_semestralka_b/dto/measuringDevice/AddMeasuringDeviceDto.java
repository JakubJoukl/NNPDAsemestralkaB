package com.example.nnpda_semestralka_b.dto.measuringDevice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AddMeasuringDeviceDto {
    @Getter
    @Setter
    String deviceName;

    @Getter
    @Setter
    private List<AddMeasuringDeviceSensorDto> sensors;
}
