package in.zidiolearning.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import in.zidiolearning.Entity.User;
import in.zidiolearning.ExpenseEntity.Expense;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String approverUsername);
}
