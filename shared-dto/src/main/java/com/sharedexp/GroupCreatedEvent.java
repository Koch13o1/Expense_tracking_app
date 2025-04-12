package com.sharedexp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupCreatedEvent {
    private Long groupId;
    private String name;
    private List<Long> memberIds;
}


