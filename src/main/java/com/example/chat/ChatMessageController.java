package com.example.chat;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat.send")  // /app/chat.send 로 매핑됨
    public void handleMessage(@Payload ChatMessageRequest request) {
        chatMessageService.sendMessage(request);  // 내부에서 convertAndSend 실행
    }

    @GetMapping("/api/chat/rooms/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageEntity> getMessages(@PathVariable Long roomId) {
        return chatMessageService.getMessages(roomId);
    }
}


