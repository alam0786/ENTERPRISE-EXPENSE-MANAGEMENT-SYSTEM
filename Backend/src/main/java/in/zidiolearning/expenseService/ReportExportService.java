package in.zidiolearning.expenseService;


import in.zidiolearning.ExpenseDto.ExpenseResponse;
import in.zidiolearning.ExpenseEntity.Expense;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class ReportExportService {

    private final ExpenseService expenseService;

    public ByteArrayInputStream exportToExcel(List<ExpenseResponse> expenses) throws Exception {
        String[] headers = {"ID", "Title", "Amount", "Category", "Status", "Date"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Expenses");
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
            }

            int rowIdx = 1;
            for (ExpenseResponse expense : expenses) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(expense.getId());
                row.createCell(1).setCellValue(expense.getTitle());
                row.createCell(2).setCellValue(expense.getAmount());
                row.createCell(3).setCellValue(expense.getCategory().toString());
                row.createCell(4).setCellValue(expense.getStatus() != null ? expense.getStatus().toString() : "N/A");
                row.createCell(5).setCellValue(expense.getExpenseDate().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    public ByteArrayInputStream exportToPDF(List<ExpenseResponse> expenses) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Paragraph title = new Paragraph("Expense Report", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        Stream.of("ID", "Title", "Amount", "Category", "Status", "Date")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(headerTitle));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(header);
                });

        for (ExpenseResponse e : expenses) {
            table.addCell(e.getId().toString());
            table.addCell(e.getTitle());
            table.addCell(e.getAmount().toString());
            table.addCell(e.getCategory().toString());
            table.addCell(e.getStatus() != null ? e.getStatus().toString() : "N/A");
            table.addCell(e.getExpenseDate().toString());
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }
}
