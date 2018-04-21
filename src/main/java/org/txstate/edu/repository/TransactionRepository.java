package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.Transaction;

/**
 * Created by jyoti on 4/21/18.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
