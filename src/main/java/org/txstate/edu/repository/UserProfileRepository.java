package org.txstate.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.txstate.edu.model.UsersProfile;

/**
 * Created by jyoti on 4/2/18.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UsersProfile, String> {

}
