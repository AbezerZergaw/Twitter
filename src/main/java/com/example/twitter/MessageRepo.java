package com.example.twitter;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepo  extends CrudRepository<Message, Long> {
}
