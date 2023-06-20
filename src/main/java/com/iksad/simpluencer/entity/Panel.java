package com.iksad.simpluencer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "panel", indexes = {
        @Index(columnList = "agent_id")
})
@NoArgsConstructor @Getter @Setter
public class Panel extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    private String provider;

    private String principalName;

    private String description;

    private int location;
}
