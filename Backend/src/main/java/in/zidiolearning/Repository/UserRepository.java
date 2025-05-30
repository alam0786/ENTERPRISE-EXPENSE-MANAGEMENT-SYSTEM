package in.zidiolearning.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import in.zidiolearning.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
