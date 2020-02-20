package com.wildcodeschool.WildCircus.repository;

import com.wildcodeschool.WildCircus.entity.Wilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WilderRepository extends JpaRepository<Wilder,Long> {
}
