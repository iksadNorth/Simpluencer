package com.iksad.simpluencer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "notice", indexes = {
        @Index(columnList = "writer_id")
})
@NoArgsConstructor @Getter @Setter
public class Notice extends BaseEntity {
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Agent writer;

    private String content;

    private String imageUri;

    @OneToMany(mappedBy = "notice", cascade = {CascadeType.REMOVE})
    private Collection<NotifiedPanel> notifiedPanels;
}
