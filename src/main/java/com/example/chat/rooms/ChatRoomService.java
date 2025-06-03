package com.example.chat.rooms;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    public ChatRoomResponse findOrCreateRoom(Long userAId, Long userBId) {
        // 항상 동일한 roomKey를 만들기 위해 정렬
        String roomKey = Stream.of(userAId, userBId)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));  // "3-7" 같은 형식

        ChatRoom room = chatRoomRepository.findByRoomKey(roomKey)
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .roomKey(roomKey)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });

        // 엔티티를 응답 DTO로 변환
        return new ChatRoomResponse(
                room.getId(),
                room.getRoomKey()
        );
    }


    // 내 채팅방 목록 조회
    public List<ChatRoomResponse> getMyChatRooms(Long userId) {
        Member member = memberRepository.getReferenceById(userId);
        List<ChatRoom> rooms = chatRoomRepository.findBySenderOrReceiver(member, member);
        return rooms.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 엔티티 → DTO 변환
    private ChatRoomResponse toResponse(ChatRoom room) {
        return new ChatRoomResponse(room.getId(), room.getRoomKey());
    }
}
