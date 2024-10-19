package com.example.nnpda_semestralka_b.repositories;

import com.example.nnpda_semestralka_b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> getUserByUsername(String username);
}