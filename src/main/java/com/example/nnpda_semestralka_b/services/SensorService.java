package com.example.nnpda_semestralka_b.services;

import com.example.nnpda_semestralka_b.dto.sensor.SensorDto;
import com.example.nnpda_semestralka_b.entity.MeasuringDevice;
import com.example.nnpda_semestralka_b.entity.Sensor;
import com.example.nnpda_semestralka_b.entity.User;
import com.example.nnpda_semestralka_b.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MeasuringDeviceService measuringDeviceService;

    @Autowired
    private ModelMapper modelMapper;

    public void saveSensor(Sensor sensor){
        sensorRepository.save(sensor);
    }

    public List<Sensor> getAllSensors(){
        return sensorRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addSensor(String sensorName, String deviceName, User user) {
        MeasuringDevice measuringDevice = measuringDeviceService.getUserMeasuringDeviceByName(deviceName, user);
        if(measuringDevice == null){
            return false;
        } else {
            Sensor sensor = new Sensor(sensorName, measuringDevice);
            measuringDevice.getSensors().add(sensor);
            saveSensor(sensor);
            measuringDeviceService.saveMeasuringDevice(measuringDevice);
            return true;
        }
    }

    //TODO asi bych neměl být schopný přenést senzor na jiné zařízení?
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMeasuringDeviceSensor(String sensorName, String newSensorName, String measuringDeviceName, User user) {
        MeasuringDevice measuringDevice = measuringDeviceService.getUserMeasuringDeviceByName(measuringDeviceName, user);
        if(measuringDevice == null) return false;

        Sensor sensor = sensorRepository.getSensorBySensorNameAndMeasuringDevice(sensorName, measuringDevice).orElse(null);
        if(sensor != null){
            sensor.setSensorName(newSensorName);
            sensorRepository.save(sensor);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMeasuringDeviceSensor(String sensorName, String deviceName, User user) {
        MeasuringDevice measuringDevice = measuringDeviceService.getUserMeasuringDeviceByName(deviceName, user);
        if(measuringDevice == null) return false;
        Sensor sensor = sensorRepository.getSensorBySensorNameAndMeasuringDevice(sensorName, measuringDevice).orElse(null);
        if(sensor == null) return false;
        else {
            measuringDevice.getSensors().remove(sensor);
            sensorRepository.delete(sensor);
            return true;
        }
    }

    public Sensor getSensorByName(String sensorName, String deviceName, User user){
        MeasuringDevice measuringDevice = measuringDeviceService.getUserMeasuringDeviceByName(deviceName, user);
        if(measuringDevice == null) return null;
        else {
            return sensorRepository.getSensorBySensorNameAndMeasuringDevice(sensorName, measuringDevice).orElse(null);
        }
    }

    public SensorDto convertToDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorDto.class);
    }

    public List<SensorDto> convertToDtoList(List<Sensor> sensors) {
        return sensors.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Sensor convertToEntity(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }

    public List<Sensor> convertToEntityList(List<SensorDto> sensorDtos) {
        return sensorDtos.stream()
                .map(sensorDto -> new Sensor(sensorDto.getSensorName()))
                .collect(Collectors.toList());
    }
}
