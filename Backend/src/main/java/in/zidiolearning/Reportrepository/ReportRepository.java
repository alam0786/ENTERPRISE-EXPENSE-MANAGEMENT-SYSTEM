package in.zidiolearning.Reportrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.zidiolearning.ReportEntity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	List<Report> findByUserId(Long userId);
}
