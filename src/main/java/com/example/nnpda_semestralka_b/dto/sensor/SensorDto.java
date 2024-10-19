package com.example.nnpda_semestralka_b.dto.sensor;

import com.example.nnpda_semestralka_b.dto.measuringDevice.MeasuringDeviceDto;
import lombok.Getter;
import lombok.Setter;

public class SensorDto {
    @Getter
    @Setter
    private MeasuringDeviceDto measuringDeviceDto;

    @Getter
    @Setter
    private String sensorName;

    @Getter
    @Setter
    private Integer measuredValue;
}
