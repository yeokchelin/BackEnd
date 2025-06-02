package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //우선 JpaRepository/ListCrudRepository/CrudRepository의
    //<S extends T> S save(S entity); 만 사용할거라 안넣을게요
}
