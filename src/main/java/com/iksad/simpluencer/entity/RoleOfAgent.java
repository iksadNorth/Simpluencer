package com.iksad.simpluencer.entity;

import com.iksad.simpluencer.type.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "role_of_agent") @IdClass(RoleOfAgent.RoleOfAgentKey.class)
@NoArgsConstructor @Getter @Setter
public class RoleOfAgent {
    @NoArgsConstructor @Getter @Setter
    public class RoleOfAgentKey implements Serializable {
        private static final long serialVersionUID = 3735029399522186249L;
        private Agent agent;
        private RoleType role;
    }

    @Id @ManyToOne
    private Agent agent;
    @Id @Convert(converter = RoleType.ConverterImpl.class)
    private RoleType role;
}
