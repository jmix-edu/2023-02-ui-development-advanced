package com.company.jmixpm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.JmixEntity;

import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
public class IncomeExpensesDto {
    @JmixGeneratedValue
    @JmixId
    private UUID id;

    private BigDecimal income;

    private BigDecimal expenses;

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getExpenses() {
        return expenses;
    }

    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}