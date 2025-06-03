package com.example.chat.rooms;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody ChatRoomCreateRequest request) {
        ChatRoomResponse response = chatRoomService.findOrCreateRoom(
                request.getSenderId(),
                request.getReceiverId()
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getMyRooms(@RequestParam Long userId) {
        List<ChatRoomResponse> responseList = chatRoomService.getMyChatRooms(userId);
        return ResponseEntity.ok(responseList);
    }
}
