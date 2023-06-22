package com.iksad.simpluencer.entity;

import com.iksad.simpluencer.type.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "role_of_agent", indexes = {
        @Index(columnList = "agent_id"),
        @Index(columnList = "role")
})
@NoArgsConstructor @Getter @Setter
public class RoleOfAgent extends BaseEntity {
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;
    @Convert(converter = RoleType.ConverterImpl.class)
    private RoleType role;

    public static RoleOfAgent of(RoleType role) {
        RoleOfAgent entity = new RoleOfAgent();
        entity.setRole(role);
        return entity;
    }
}
