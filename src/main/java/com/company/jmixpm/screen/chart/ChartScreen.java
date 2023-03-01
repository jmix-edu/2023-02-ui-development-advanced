package com.company.jmixpm.screen.chart;

import com.company.jmixpm.app.IncomeExpensesService;
import com.company.jmixpm.entity.IncomeExpensesDto;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("ChartScreen")
@UiDescriptor("chart-screen.xml")
public class ChartScreen extends Screen {
    @Autowired
    private IncomeExpensesService incomeExpensesService;

    @Install(to = "incomeExpensesDtoesDl", target = Target.DATA_LOADER)
    private List<IncomeExpensesDto> incomeExpensesDtoesDlLoadDelegate(LoadContext<IncomeExpensesDto> loadContext) {
        return incomeExpensesService.loadIncomeExpenses();
    }
}