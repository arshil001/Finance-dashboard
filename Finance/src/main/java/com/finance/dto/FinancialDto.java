package com.finance.dto;

import com.finance.enums.RecordType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
public class FinancialDto {
    @NotNull
    private Double amount;

    @NotNull
    private RecordType type;

    @NotBlank
    private String category;

    private LocalDate date;
    private String description;
}
