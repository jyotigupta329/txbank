package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.Roles;

/**
 * Created by jyoti on 4/21/18.
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRole(String role);
}
