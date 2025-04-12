package com.sharedexp.group_service.controller;

import com.sharedexp.GroupCreatedEvent;
import com.sharedexp.group_service.kafka.GroupEventProducer;
import com.sharedexp.group_service.model.Group;
import com.sharedexp.group_service.repository.GroupRepository;
import org.apache.kafka.common.errors.GroupNotEmptyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupRepository groupRepository;
    private final GroupEventProducer kafkaProducer;

    public GroupController(GroupRepository groupRepository, GroupEventProducer kafkaProducer){
        this.groupRepository = groupRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    public Group createGroup(@RequestBody Group group){
        Group savedGroup = groupRepository.save(group);

        // Creating and publishing kafka event
        GroupCreatedEvent event = new GroupCreatedEvent();
        event.setGroupId(savedGroup.getId());
        event.setName(savedGroup.getName());
        event.setMemberIds(savedGroup.getMemberIds());

        kafkaProducer.sendGroupCreatedEvent(event);

        return savedGroup;
    }

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }
}
