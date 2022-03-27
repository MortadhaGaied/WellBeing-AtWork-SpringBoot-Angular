package com.wellbeignatwork.backend.repository.Chat;


import com.wellbeignatwork.backend.entity.Chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    ChatRoom findByUniqueKey(String uniqueKey);

}
