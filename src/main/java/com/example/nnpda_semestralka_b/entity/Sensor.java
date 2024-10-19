package com.example.nnpda_semestralka_b.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(
        name="SENSOR",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"sensor_name", "measuring_device_measuring_device_id"})
)
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sensorId;

    @Column
    @NotBlank
    private String sensorName;

    @ManyToOne
    @NotNull
    private MeasuringDevice measuringDevice;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeasuredValue> measuredValues = new ArrayList<>();

    public Sensor(String sensorName){
        this.sensorName = sensorName;
    }

    public Sensor(String sensorName, MeasuringDevice measuringDevice){
        this.sensorName = sensorName;
        this.measuringDevice = measuringDevice;
    }
}
