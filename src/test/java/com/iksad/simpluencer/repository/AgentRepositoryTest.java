package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.config.PasswordEncoderConfig;
import com.iksad.simpluencer.entity.Agent;
import com.iksad.simpluencer.entity.RoleOfAgent;
import com.iksad.simpluencer.fixture.AgentFixture;
import com.iksad.simpluencer.model.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Iterator;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ActiveProfiles(profiles = {"datasource-local", "h2db-local"})
@DataJpaTest
@DisplayName("[AgentRepository]")
class AgentRepositoryTest {
    @Autowired
    private AgentRepository agentRepository;

    @Test @DisplayName("[findByEmail][정상]")
    @Sql(scripts = {"classpath:/data_init/create_agent.sql"})
    void findByEmail() {
        // Given - 이메일을
        String email = "mock email";

        // When
        Optional<Agent> optional = agentRepository.findByEmail(email);

        // then
        assertThat(optional.isPresent()).isTrue();
        optional.ifPresent(agent -> log.info(agent.toString()));

    }

    @Test @DisplayName("[save][비정상] 이미 존재하는 이메일")
    @Sql(scripts = {"classpath:/data_init/create_agent.sql"})
    void save_already_exist() {
        // Given - 이미 존재하는 이메일인 경우,
        Agent entity = new Agent();
        entity.setEmail("mock email");
        entity.setNickname("1");
        entity.setPassword("1");

        // When - 조회 시,
        // then - 다음과 같은 결과가 나옴.
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> {
            agentRepository.save(entity);
        });

        String message = e.getMessage();
        log.info("e.getMessage()\n{}", message);
        assertThat(message).isEqualTo("""
                could not execute statement [Unique index or primary key violation: "PUBLIC.CONSTRAINT_INDEX_3 ON PUBLIC.AGENT(EMAIL NULLS FIRST) VALUES ( /* 2 */ 'mock email' )"; SQL statement:
                insert into agent (created_at,email,introduction,nickname,password,profile_image,id) values (?,?,?,?,?,?,default) [23505-214]] [insert into agent (created_at,email,introduction,nickname,password,profile_image,id) values (?,?,?,?,?,?,default)]; SQL [insert into agent (created_at,email,introduction,nickname,password,profile_image,id) values (?,?,?,?,?,?,default)]; constraint ["PUBLIC.CONSTRAINT_INDEX_3 ON PUBLIC.AGENT(EMAIL NULLS FIRST) VALUES ( /* 2 */ 'mock email' )"; SQL statement:
                insert into agent (created_at,email,introduction,nickname,password,profile_image,id) values (?,?,?,?,?,?,default) [23505-214]]
                """.trim());
    }

    @Test @DisplayName("[save][비정상] 빈 칸으로 들어온 이메일")
    @Sql(scripts = {"classpath:/data_init/create_agent.sql"})
    void save_as_null() {
        // Given - 이미 존재하는 이메일인 경우,
        Agent entity = new Agent();
        entity.setEmail(null);
        entity.setNickname("1");
        entity.setPassword("1");

        // When - 조회 시,
        // then - 다음과 같은 결과가 나옴.
        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> {
            agentRepository.save(entity);
        });

        String message = e.getMessage();
        log.info("e.getMessage()\n{}", message);
        assertThat(message).isEqualTo("""
                not-null property references a null or transient value : com.iksad.simpluencer.entity.Agent.email
                """.trim());
    }

    @Test @DisplayName("[save][정상] Cascade.Persist 작동 여부")
    void save() {
        // Given - 저장되지 않은 유저가 제공되면
        UserRequest fixture = AgentFixture.userRequest1();

        // When - 해당 유저를 저장한다.
        Agent saved = agentRepository.save(fixture.toEntity(new PasswordEncoderConfig.IdentityPasswordEncoder()));

        // then - 해당 유저 뿐만 아니라 RoleOfAgent도 저장된다. ID 값이 부여되었는지 확인해서 영속성 여부 판단.
        Iterator<RoleOfAgent> iterator = saved.getRoles().iterator();
        assertThat(iterator.hasNext()).isTrue();

        RoleOfAgent entity = iterator.next();
        assertThat(entity.getId()).isNotNull();
        log.info(String.valueOf(entity.getId()));

    }

    @Test @DisplayName("[findWithPanelsById][정상]")
    void findWithPanelsById() {
        // Given
        long id = 1L;

        // When
        Optional<Agent> optional = agentRepository.findWithPanelsById(id);

        // then
        assertThat(optional.isPresent()).isTrue();

    }
}