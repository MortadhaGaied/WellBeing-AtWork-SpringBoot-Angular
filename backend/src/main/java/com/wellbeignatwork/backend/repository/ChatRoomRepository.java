package com.wellbeignatwork.backend.repository;


import com.wellbeignatwork.backend.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    ChatRoom findByUniqueKey(String uniqueKey);

}
