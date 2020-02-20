package com.wildcodeschool.WildCircus.repository;

import com.wildcodeschool.WildCircus.entity.Client;
import com.wildcodeschool.WildCircus.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findTop2ByOrderByDateAsc();
}
