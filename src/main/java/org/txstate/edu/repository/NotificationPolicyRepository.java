package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.NotificationPolicy;

/**
 * Created by jyoti on 4/22/18.
 */
@Repository
public interface NotificationPolicyRepository extends JpaRepository<NotificationPolicy, Long> {
    NotificationPolicy findByUsername(String username);
}
