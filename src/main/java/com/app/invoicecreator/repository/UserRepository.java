package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    User findByUsername(String username);
}
