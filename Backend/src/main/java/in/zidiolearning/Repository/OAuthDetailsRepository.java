package in.zidiolearning.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.zidiolearning.Entity.OAuthDetails;

@Repository
public interface OAuthDetailsRepository extends JpaRepository<OAuthDetails, Long> {
    Optional<OAuthDetails> findByProviderAndProviderId(String provider, String providerId);
}