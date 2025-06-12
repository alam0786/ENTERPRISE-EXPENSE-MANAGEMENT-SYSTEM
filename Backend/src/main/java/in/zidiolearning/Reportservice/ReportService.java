package in.zidiolearning.Reportservice;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zidiolearning.ReportEntity.Report;
import in.zidiolearning.Reportrepository.ReportRepository;

@Service
public class ReportService {
	@Autowired
	private ReportRepository reportRepository;

	public Report generateReport(Report report) {
		report.setGenrateDate(LocalDate.now());
		return reportRepository.save(report);
	}

	public List<Report> getUserReports(Long userId) {
		return reportRepository.findByUserId(userId);
	}
}
