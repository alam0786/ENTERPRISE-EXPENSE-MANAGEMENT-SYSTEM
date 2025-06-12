package in.zidiolearning.Approvalservice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zidiolearning.Approvalentity.Approval;
import in.zidiolearning.Approvalrepository.ApprovalRepository;

@Service
public class ApprovalService 
{

	@Autowired
	private ApprovalRepository approvalRepository;
	
	public Approval createApproval(Approval approval) {
		approval.setDecision("PENDING");
		return approvalRepository.save(approval);
	}
	
	public void approveExpense(Long approvalId, String decision, String remarks) {
		 Approval approval = approvalRepository.findById(approvalId).orElseThrow();
		 approval.setDecision(decision);
		 approval.setDecisionDate(LocalDateTime.now());
		 approval.setRemarks(remarks);
		 approvalRepository.save(approval);
	}
	public List<Approval> getApprovalsByExpense(Long expenseId) {
		 return approvalRepository.findByExpenseid(expenseId);
		 }

	
}
