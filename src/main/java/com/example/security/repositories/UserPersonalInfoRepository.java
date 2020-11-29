package com.example.security.repositories;

import com.example.security.entities.UserPersonalInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPersonalInfoRepository extends CrudRepository<UserPersonalInfo, Long> {
}
