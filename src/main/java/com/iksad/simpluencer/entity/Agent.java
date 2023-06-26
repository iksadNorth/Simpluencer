package com.iksad.simpluencer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.HashSet;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "agent", indexes = {
        @Index(columnList = "email")
})
@NoArgsConstructor @Getter @Setter
public class Agent extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileImage;

    private String introduction;

    @OneToMany(mappedBy = "agent", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Collection<RoleOfAgent> roles;

    @OneToMany(mappedBy = "agent", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Collection<Panel> panels;

    @OneToMany(mappedBy = "writer", cascade = {CascadeType.REMOVE})
    private Collection<Notice> notices;

    public void setRoles(Collection<RoleOfAgent> roles) {
        throw new UnsupportedOperationException();
    }

    public void addRole(RoleOfAgent role) {
        if(this.roles == null)
            this.roles = new HashSet<>();

        role.setAgent(this);
        this.roles.add(role);
    }
}
