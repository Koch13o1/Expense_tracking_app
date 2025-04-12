package com.sharedexp.group_service.repository;

import com.sharedexp.group_service.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
