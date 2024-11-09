package com.example.nnpda_semestralka_b.dto.measuringDevice;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

public class MeasuredValueDto {
    @Getter
    @Setter
    private Integer measuredValueId;

    @Getter
    @Setter
    private Instant measuredOn;

    @Getter
    @Setter
    private Integer measuredValue;

    @Getter
    @Setter
    private Integer sensorId;

    public MeasuredValueDto(Integer measuredValueId, Instant measuredOn, Integer measuredValue, Integer sensorId) {
        this.measuredValueId = measuredValueId;
        this.measuredOn = measuredOn;
        this.measuredValue = measuredValue;
        this.sensorId = sensorId;
    }

    public MeasuredValueDto(){

    }
}
