package com.example.tdiframework.reponsitory;

import com.example.tdiframework.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByPhone(String phone);

    @Query("SELECT max(customerId) FROM User")
    String findMaxCustomerId();

    User findByCustomerId(String customerId);
}
