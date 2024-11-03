package com.example.nnpda_semestralka_b.repositories;

import com.example.nnpda_semestralka_b.entity.MeasuringDevice;
import com.example.nnpda_semestralka_b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeasuringDeviceRepository extends JpaRepository<MeasuringDevice, Integer> {
    public Optional<MeasuringDevice> getMeasuringDeviceByDeviceNameAndUser(String measuringDeviceName, User username);
}
