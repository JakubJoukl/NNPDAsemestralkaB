package com.example.nnpda_semestralka_b.controllers;

import com.example.nnpda_semestralka_b.dto.measuringDevice.AddMeasuringDeviceDto;
import com.example.nnpda_semestralka_b.dto.measuringDevice.DeleteMeasuringDeviceDto;
import com.example.nnpda_semestralka_b.dto.measuringDevice.UpdateMeasuringDeviceDto;
import com.example.nnpda_semestralka_b.entity.MeasuringDevice;
import com.example.nnpda_semestralka_b.entity.Sensor;
import com.example.nnpda_semestralka_b.entity.User;
import com.example.nnpda_semestralka_b.services.MeasuringDeviceService;
import com.example.nnpda_semestralka_b.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/device")
public class MeasuringDeviceController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeasuringDeviceService measuringDeviceService;

    @PostMapping("/addDevice")
    public ResponseEntity<String> addDevice(@RequestBody AddMeasuringDeviceDto addMeasuringDeviceDto){
        User user = userService.getUserFromContext();
        if(user == null) return ResponseEntity.status(404).body("User not found");

        List<Sensor> sensors = measuringDeviceService.convertAddMeasuringDeviceSensorDtoToEntityList(addMeasuringDeviceDto.getSensors());
        boolean deviceAdded = measuringDeviceService.addMeasuringDevice(addMeasuringDeviceDto.getDeviceName(), sensors, user);

        if(deviceAdded) return ResponseEntity.status(200).body("Device registered");
        else return ResponseEntity.status(500).body("Failed to add device");
    }

    @DeleteMapping("/deleteDevice")
    public ResponseEntity<String> deleteDevice(@RequestBody DeleteMeasuringDeviceDto deleteMeasuringDeviceDto){
        User user = userService.getUserFromContext();
        if(user == null) return ResponseEntity.status(404).body("User not found");
        boolean deviceAdded = measuringDeviceService.deleteUserMeasuringDevice(deleteMeasuringDeviceDto.getDeviceName(), user);

        if(deviceAdded) return ResponseEntity.status(200).body("Device deleted");
        else return ResponseEntity.status(500).body("Failed to delete device");
    }

    @GetMapping("/getDevice/{deviceName}")
    public ResponseEntity<?> getDevice(@PathVariable("deviceName") String deviceName){
        User user = userService.getUserFromContext();
        if(user == null) return ResponseEntity.status(404).body("User not found");

        MeasuringDevice measuringDevice = measuringDeviceService.getDevice(deviceName, user);
        if(measuringDevice == null) return ResponseEntity.status(404).body("Device not found");
        return ResponseEntity.status(200).body(measuringDeviceService.convertToDto(measuringDevice));
    }

    @PutMapping("/updateDevice")
    public ResponseEntity<String> updateDevice(@RequestBody UpdateMeasuringDeviceDto updateMeasuringDeviceDto){
        User user = userService.getUserFromContext();
        if(user == null) return ResponseEntity.status(404).body("User not found");

        List<Sensor> sensors = measuringDeviceService.convertAddMeasuringDeviceSensorDtoToEntityList(updateMeasuringDeviceDto.getSensors());
        boolean deviceUpdated = measuringDeviceService.updateUserDevice(updateMeasuringDeviceDto.getDeviceName(), updateMeasuringDeviceDto.getNewDeviceName(), sensors, user);

        if(deviceUpdated) return ResponseEntity.status(200).body("Device updated");
        else return ResponseEntity.status(500).body("Failed to update device");
    }

    @GetMapping("/getDevices")
    public ResponseEntity<?> getDevices(){
        try {
            List<MeasuringDevice> measuringDevices = measuringDeviceService.getMeasuringDevicesByUser(userService.getUserFromContext());
            return ResponseEntity.status(200).body(measuringDeviceService.convertToDto(measuringDevices));
        } catch (Exception e){
            return ResponseEntity.status(404).body("Failed to get user devices");
        }
    }
}
