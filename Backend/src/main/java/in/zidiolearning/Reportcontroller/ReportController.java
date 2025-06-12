package in.zidiolearning.Reportcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.zidiolearning.ReportEntity.Report;
import in.zidiolearning.Reportdto.ReportDto;
import in.zidiolearning.Reportservice.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
	private ReportService reportService;

	// Generate new report
	@PostMapping("/generate")
	public ResponseEntity<Report> generateReport(@RequestBody ReportDto dto) {
		Report report = new Report();
		report.setReportTye(dto.reportTye);
		report.setFileUrl(dto.fileUrl);
		report.setUserId(dto.userId);
		// generateDate will automatically set inside service
		return ResponseEntity.ok(reportService.generateReport(report));
	}

	// Get all reports of a user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Report>> getReportsByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(reportService.getUserReports(userId));
	}
}
