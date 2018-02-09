package org.txstate.edu.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.txstate.edu.model.Users;

/**
 * Created by jyoti on 2/8/18.
 */

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUsername(String username);
}
