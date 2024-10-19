package com.example.nnpda_semestralka_b.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
public class MeasuredValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer measuredValueId;

    @Column
    private Instant measuredOn;

    @Column
    private Integer measuredValue;

    @ManyToOne
    private Sensor sensor;

    public MeasuredValue(Sensor sensor, Instant measuredOn, Integer measuredValue){
        this.measuredOn = measuredOn;
        this.measuredValue = measuredValue;
        this.sensor = sensor;
        sensor.getMeasuredValues().add(this);
    }
}
