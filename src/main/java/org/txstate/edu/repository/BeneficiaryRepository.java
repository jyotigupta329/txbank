package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.BeneficiaryAccount;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<BeneficiaryAccount, Integer> {

    List<BeneficiaryAccount> getByUsername(String username);
}
