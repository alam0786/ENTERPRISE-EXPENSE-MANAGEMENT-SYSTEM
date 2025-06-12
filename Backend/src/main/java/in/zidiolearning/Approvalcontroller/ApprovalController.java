package in.zidiolearning.Approvalcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.zidiolearning.Approvaldto.ApprovalDto;
import in.zidiolearning.Approvalentity.Approval;
import in.zidiolearning.Approvalservice.ApprovalService;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

	@Autowired
	private ApprovalService approvalService;

	@PostMapping("/create")
	public ResponseEntity<Approval> createApproval(@RequestBody ApprovalDto dto) {
		Approval approval = Approval.builder().expenseid(dto.expenseid).approvalid(dto.approvalid).level(dto.level)
				.decision("PENDING").remarks(dto.remarks).decisionDate(null).build();
		return ResponseEntity.ok(approvalService.createApproval(approval));
	}

	@PostMapping("/decision/{approvalId}")
	public ResponseEntity<String> approveExpense(@PathVariable Long approvalId, @RequestParam String decision,
			@RequestParam(required = false) String remarks) {
		approvalService.approveExpense(approvalId, decision, remarks);
		return ResponseEntity.ok("Decision Updated Successfully");
	}

	@GetMapping("/expense/{expenseId}")
	public ResponseEntity<List<Approval>> getApprovalsByExpense(@PathVariable Long expenseId) {
		return ResponseEntity.ok(approvalService.getApprovalsByExpense(expenseId));
	}
}
