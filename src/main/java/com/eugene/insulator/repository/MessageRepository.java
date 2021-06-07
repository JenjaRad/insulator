package com.eugene.insulator.repository;

import com.eugene.insulator.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message,Long> {
    List<Message> findByText(String text);
}
