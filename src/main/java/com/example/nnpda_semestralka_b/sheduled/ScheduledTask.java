package com.example.nnpda_semestralka_b.sheduled;

import com.example.nnpda_semestralka_b.entity.MeasuredValue;
import com.example.nnpda_semestralka_b.entity.Sensor;
import com.example.nnpda_semestralka_b.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Component
public class ScheduledTask {

    @Autowired
    private SensorService sensorService;

    Random random = new Random();

    @Scheduled(fixedRate = 1000)
    @Transactional(rollbackFor = Exception.class)
    public void performTask() {
        List<Sensor> sensors = sensorService.getAllSensors();
        if(sensors.isEmpty()) return;
        Integer randomValue = random.nextInt(100);
        Integer sensorIndex = random.nextInt(sensors.size());
        Sensor sensor = sensors.get(sensorIndex);
        System.out.println("Measured value " + randomValue + " sensor that measured that value: " + sensor.getSensorName());
        MeasuredValue measuredValue = new MeasuredValue(sensor, Instant.now(), randomValue);
        sensorService.saveSensor(sensor);
    }
}
