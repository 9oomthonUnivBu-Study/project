package com.example.chat.rooms;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderAndReceiver(Member sender, Member receiver);
    List<ChatRoom> findBySenderOrReceiver(Member sender, Member receiver);
    Optional<ChatRoom> findByRoomKey(String roomKey);
}
