package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer>{
    //Wrote my own custom method (Named QUERY) for retrieving messages with given account_id/posted by
    //List<Message> findAllByPostedBy(Integer PostedBy);//Spring Boot will automatically implement this method

    //Alternative Custom Spring Query Method
    @Query("FROM Message WHERE PostedBy= :PostedBy")
    List <Message> findAllByPostedBy(@Param("PostedBy") Integer PostedBy);



}