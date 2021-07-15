package com.info.prescription.repository;

import com.info.prescription.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByUserType(String userType);

//    void delete(Optional<User> user);
}
