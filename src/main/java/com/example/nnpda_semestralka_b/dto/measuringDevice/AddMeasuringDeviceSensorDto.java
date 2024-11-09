package com.example.nnpda_semestralka_b.dto.measuringDevice;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AddMeasuringDeviceSensorDto {
    @Getter
    @Setter
    private String sensorName;

    @Getter
    @Setter
    private List<MeasuredValueDto> measuredValues;
}
