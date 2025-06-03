package com.example.chat;

import com.example.chat.ChatMessageEntity;
import com.example.chat.rooms.ChatRoom;
import com.example.chat.rooms.ChatRoomRepository;
import com.example.chat.rooms.Member;
import com.example.chat.rooms.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(ChatMessageRequest request) {
        ChatRoom room = chatRoomRepository.getReferenceById(request.getRoomId());
        Member sender = memberRepository.getReferenceById(request.getSenderId());


        ChatMessageEntity entity = ChatMessageEntity.builder()
                .room(room)
                .sender(sender)
                .message(request.getMessage())
                .sentAt(LocalDateTime.now())
                .build();

        chatMessageRepository.save(entity);

        // WebSocket으로 전송 (구독자들에게)
        messagingTemplate.convertAndSend(
                "/topic/chat/room/" + room.getId(),
                request
        );
    }

    public List<ChatMessageEntity> getMessages(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }
}

