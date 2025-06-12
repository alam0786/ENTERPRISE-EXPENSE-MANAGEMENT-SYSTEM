package in.zidiolearning.Approvalrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.zidiolearning.Approvalentity.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long>
{

	List<Approval> findByExpenseid(Long epenseid);
	
}
