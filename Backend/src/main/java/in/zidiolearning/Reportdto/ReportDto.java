package in.zidiolearning.Reportdto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportDto {

	public String reportTye; // Monthly, Yearly
	public String fileUrl; // Path to generated PDF or Excel
	public LocalDate genrateDate;
	public Long userId;
}
