package com.example.chat.rooms;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatRoomResponse {
    private Long roomId;
    private String roomKey;
    private Long senderId;
    private Long receiverId;
    private LocalDateTime createdAt;

    public ChatRoomResponse(Long id, String roomKey) {
        this.roomId = id;
        this.roomKey = roomKey;
    }
}


