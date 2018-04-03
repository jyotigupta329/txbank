package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.txstate.edu.model.Users;

/**
 * Created by jyoti on 4/2/18.
 */
public interface UserProfileRepository extends JpaRepository<Users, String> {

}
