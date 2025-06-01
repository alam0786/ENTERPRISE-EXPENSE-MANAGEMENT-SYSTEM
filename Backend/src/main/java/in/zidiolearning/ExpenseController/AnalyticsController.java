package in.zidiolearning.ExpenseController;


import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import in.zidiolearning.ExpenseDto.ExpenseResponse;
import in.zidiolearning.ExpenseEntity.Expense;
import in.zidiolearning.analytics.dto.CategoryExpenseDTO;
import in.zidiolearning.analytics.dto.MonthlyExpenseDTO;
import in.zidiolearning.expenseService.AnalyticsService;
import in.zidiolearning.expenseService.ExpenseService;
import in.zidiolearning.expenseService.ReportExportService;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final ExpenseService expenseService;
    private final ReportExportService reportExportService;
    

    @GetMapping("/monthly")
    public List<MonthlyExpenseDTO> getMonthlyTrends(@RequestParam int year) {
        return analyticsService.getMonthlyTrends(year);
    }

    @GetMapping("/category")
    public List<CategoryExpenseDTO> getCategoryBreakdown(@RequestParam int year) {
        return analyticsService.getCategoryBreakdown(year);
    }
    
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() throws Exception {
        List<ExpenseResponse> expenses = expenseService.getAllExpenses(); // filter as needed
        ByteArrayInputStream in = reportExportService.exportToExcel(expenses);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=expenses.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportToPDF() throws Exception {
        List<ExpenseResponse> expenses = expenseService.getAllExpenses();
        ByteArrayInputStream in = reportExportService.exportToPDF(expenses);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=expenses.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(in.readAllBytes());
    }


    
}
