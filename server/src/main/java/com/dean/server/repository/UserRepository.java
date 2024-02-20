package com.dean.server.repository;

import com.dean.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    public UserEntity findByUsername(String username);
    @Query("select u.msv from user_account u")
    public List<String> getAllMsv();
    @Query("select u.username from  user_account u where u.msv = ?1")
    public String getUsernameByMsv(String msv);

}
