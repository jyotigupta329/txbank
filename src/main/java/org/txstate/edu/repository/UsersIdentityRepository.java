package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.UsersIdentity;

/**
 * Created by jyoti on 4/19/18.
 */
@Repository
public interface UsersIdentityRepository extends JpaRepository<UsersIdentity, Long> {
}
