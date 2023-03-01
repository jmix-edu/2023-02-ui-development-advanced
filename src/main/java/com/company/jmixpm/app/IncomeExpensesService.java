package com.company.jmixpm.app;

import com.company.jmixpm.entity.IncomeExpensesDto;
import io.jmix.core.Metadata;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class IncomeExpensesService {
    private final Metadata metadata;

    public IncomeExpensesService(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<IncomeExpensesDto> loadIncomeExpenses() {
        return IntStream.range(2010, 2023)
                .mapToObj(year -> {
                    IncomeExpensesDto dto = metadata.create(IncomeExpensesDto.class);
                    dto.setYear(year);
                    dto.setExpenses(BigDecimal.valueOf(RandomUtils.nextDouble(10d, 50d)));
                    dto.setIncome(BigDecimal.valueOf(RandomUtils.nextDouble(10d, 50d)));
                    return dto;
                }).collect(Collectors.toList());
    }
}
