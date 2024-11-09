package com.example.nnpda_semestralka_b.services;

import com.example.nnpda_semestralka_b.dto.measuringDevice.AddMeasuringDeviceSensorDto;
import com.example.nnpda_semestralka_b.dto.measuringDevice.MeasuredValueDto;
import com.example.nnpda_semestralka_b.dto.measuringDevice.MeasuringDeviceDto;
import com.example.nnpda_semestralka_b.dto.sensor.AddSensorDto;
import com.example.nnpda_semestralka_b.entity.MeasuredValue;
import com.example.nnpda_semestralka_b.entity.MeasuringDevice;
import com.example.nnpda_semestralka_b.entity.Sensor;
import com.example.nnpda_semestralka_b.entity.User;
import com.example.nnpda_semestralka_b.repositories.MeasuringDeviceRepository;
import com.example.nnpda_semestralka_b.repositories.SensorRepository;
import com.example.nnpda_semestralka_b.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasuringDeviceService {
    @Autowired
    private MeasuringDeviceRepository measuringDeviceRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ModelMapper modelMapper;


    public void saveMeasuringDevice(MeasuringDevice measuringDevice){
        measuringDeviceRepository.save(measuringDevice);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addMeasuringDevice(String deviceName, List<Sensor> sensors, User user) {
        if(getUserMeasuringDeviceByName(deviceName, user) != null){
            return false;
        } else {
            MeasuringDevice measuringDevice = new MeasuringDevice(deviceName, sensors, user);
            sensors.forEach(sensor -> sensor.setMeasuringDevice(measuringDevice));
            user.getMeasuringDevices().add(measuringDevice);
            saveMeasuringDevice(measuringDevice);
            return true;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserDevice(String deviceName, String newDeviceName, List<Sensor> sensors, User user) {
        MeasuringDevice measuringDevice = getUserMeasuringDeviceByName(deviceName, user);
        if(measuringDevice != null){
            measuringDevice.getSensors().clear();
            sensorRepository.flush();
            measuringDevice.getSensors().addAll(sensors);
            sensors.forEach(sensor -> sensor.setMeasuringDevice(measuringDevice));
            measuringDevice.setDeviceName(newDeviceName);
            saveMeasuringDevice(measuringDevice);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserMeasuringDevice(String deviceName, User user) {
        MeasuringDevice measuringDevice = getUserMeasuringDeviceByName(deviceName, user);
        if(measuringDevice == null) return false;
        else {
            user.getMeasuringDevices().remove(measuringDevice);
            measuringDeviceRepository.delete(measuringDevice);
            return true;
        }
    }

    public List<MeasuringDevice> getMeasuringDevicesByUser(User user) {
        if(user == null) throw new RuntimeException("User not found");
        return user.getMeasuringDevices();
    }

    public MeasuringDevice getUserMeasuringDeviceByName(String deviceName, User user) {
        return user.getMeasuringDevices().stream().filter(measuringDevice1 -> measuringDevice1.getDeviceName().equals(deviceName)).findFirst().orElse(null);
    }

    public MeasuringDevice getDevice(String deviceName, User user) {
        return user.getMeasuringDevices().stream().filter(measuringDevice -> measuringDevice.getDeviceName().equals(deviceName)).findFirst().orElse(null);
    }

    public MeasuringDeviceDto convertToDto(MeasuringDevice measuringDevice) {
        MeasuringDeviceDto measuringDeviceDto = modelMapper.map(measuringDevice, MeasuringDeviceDto.class);
        List<AddMeasuringDeviceSensorDto> addSensorDtos = measuringDevice.getSensors().stream().map(sensor -> modelMapper.map(sensor, AddMeasuringDeviceSensorDto.class)).collect(Collectors.toList());
        measuringDeviceDto.setSensors(addSensorDtos);
        return measuringDeviceDto;
    }

    public List<MeasuringDeviceDto> convertToDto(List<MeasuringDevice> measuringDevices) {
        return measuringDevices.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public MeasuringDevice convertToEntity(MeasuringDeviceDto measuringDeviceDto) {
        return modelMapper.map(measuringDeviceDto, MeasuringDevice.class);
    }

    public Sensor convertAddMeasuringDeviceSensorDtoToEntity(AddMeasuringDeviceSensorDto sensorDto){
        return new Sensor(sensorDto.getSensorName());
    }

    public List<MeasuredValueDto> convertToDtoList(List<MeasuredValue> measuredValues) {
        return measuredValues.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MeasuredValueDto convertToDto(MeasuredValue measuredValue) {
        return new MeasuredValueDto(
                measuredValue.getMeasuredValueId(),
                measuredValue.getMeasuredOn(),
                measuredValue.getMeasuredValue(),
                measuredValue.getSensor().getSensorId()
        );
    }

    public List<MeasuredValue> convertToEntityList(List<MeasuredValueDto> measuredValueDtos, Sensor sensor) {
        return measuredValueDtos.stream()
                .map(dto -> convertToEntity(dto, sensor))
                .collect(Collectors.toList());
    }

    public MeasuredValue convertToEntity(MeasuredValueDto dto, Sensor sensor) {
        MeasuredValue measuredValue = new MeasuredValue();
        measuredValue.setMeasuredValueId(dto.getMeasuredValue());
        measuredValue.setMeasuredOn(dto.getMeasuredOn());
        measuredValue.setMeasuredValue(dto.getMeasuredValue());
        measuredValue.setSensor(sensor);
        return measuredValue;
    }

    public List<Sensor> convertAddMeasuringDeviceSensorDtoToEntityList(List<AddMeasuringDeviceSensorDto> sensorDtos) {
        return sensorDtos.stream()
                .map(this::convertAddMeasuringDeviceSensorDtoToEntity)
                .collect(Collectors.toList());
    }
}
