package com.sharedexp.user_service.repository;

import com.sharedexp.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
