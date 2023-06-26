package com.iksad.simpluencer.repository;

import com.iksad.simpluencer.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Slice<Notice> findByWriter_Id(Long agentId, Pageable pageable);
}
