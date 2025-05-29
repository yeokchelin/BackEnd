package com.example.StationMisyullaeng.repository;

import com.example.StationMisyullaeng.entity.PostWrite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostWrite, Long> {
}