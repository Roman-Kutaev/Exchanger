package com.example.exchanger.repository;

import com.example.exchanger.data.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Request findByConfirmationCode(int code);
}
