package com.example.nnpda_semestralka_b.repositories;

import com.example.nnpda_semestralka_b.entity.MeasuringDevice;
import com.example.nnpda_semestralka_b.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> getSensorBySensorNameAndMeasuringDevice(String sensorName, MeasuringDevice measuringDevice);

    List<Sensor> findAll();
}
