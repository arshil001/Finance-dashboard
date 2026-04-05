package com.finance.service;

import com.finance.dto.FinancialDto;
import com.finance.entity.FinancialRecord;
import com.finance.repositories.FinancialRecordRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinancialService {

    private final FinancialRecordRepo repo;

    public FinancialRecord create(FinancialDto dto) {
        FinancialRecord record = new FinancialRecord();
        BeanUtils.copyProperties(dto, record);
        return repo.save(record);
    }

    public Page<FinancialRecord> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public void softDelete(Long id) {
        FinancialRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        repo.delete(record);
    }

    public Map<String, Object> dashboard() {
        Double income = Optional.ofNullable(repo.totalIncome()).orElse(0.0);
        Double expense = Optional.ofNullable(repo.totalExpense()).orElse(0.0);

        Map<String, Object> map = new HashMap<>();
        map.put("income", income);
        map.put("expense", expense);
        map.put("balance", income - expense);

        return map;
    }
}

