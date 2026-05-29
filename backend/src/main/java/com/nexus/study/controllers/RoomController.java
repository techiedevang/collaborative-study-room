package com.nexus.study.controllers;

import com.nexus.study.models.StudyRoom;
import com.nexus.study.repositories.StudyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private StudyRoomRepository studyRoomRepository;

    @GetMapping
    public ResponseEntity<List<StudyRoom>> getAllRooms() {
        return ResponseEntity.ok(studyRoomRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<StudyRoom> createRoom(@RequestBody StudyRoom room) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        room.setCreator(username);
        
        StudyRoom savedRoom = studyRoomRepository.save(room);
        return ResponseEntity.ok(savedRoom);
    }
}
