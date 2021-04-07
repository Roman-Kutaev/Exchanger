package com.example.exchanger.repository;

import com.example.exchanger.data.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
  Currency findCourseByCc(String cc);
}
