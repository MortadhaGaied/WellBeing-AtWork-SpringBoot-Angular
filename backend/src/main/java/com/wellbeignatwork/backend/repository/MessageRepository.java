package com.wellbeignatwork.backend.repository;


import com.wellbeignatwork.backend.entity.ChatRoom;
import com.wellbeignatwork.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> getMessagesByChatroom(ChatRoom chatRoom);
}
