package com.iksad.simpluencer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "notified_panel", indexes = {
        @Index(columnList = "notice_id"),
        @Index(columnList = "panel_id")
})
@NoArgsConstructor @Getter @Setter
public class NotifiedPanel extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "panel_id")
    private Panel panel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;
}
