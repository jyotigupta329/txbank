package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.Accounts;

/**
 * Created by jyoti on 4/12/18.
 */

@Repository
public interface AccountRepository extends JpaRepository<Accounts, String> {

}
