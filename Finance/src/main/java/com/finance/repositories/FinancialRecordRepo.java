package com.finance.repositories;

import com.finance.entity.FinancialRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialRecordRepo extends JpaRepository<FinancialRecord,Long> {
    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type='INCOME' AND f.deleted=false")
    Double totalIncome();

    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type='EXPENSE' AND f.deleted=false")
    Double totalExpense();

    @Query("SELECT f.category, SUM(f.amount) FROM FinancialRecord f WHERE f.deleted=false GROUP BY f.category")
    List<Object[]> categorySummary();
}
