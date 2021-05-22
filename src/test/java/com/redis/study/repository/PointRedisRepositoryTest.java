package com.redis.study.repository;

import com.redis.study.domain.entity.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PointRedisRepositoryTest {

    @Autowired
    private PointRedisRepository pointRedisRepository;

    @AfterEach
    public void tearDown() {
        pointRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회() {
        //given
        String id = "wellbell";
        LocalDateTime refreshTime = LocalDateTime.now();
        Point point = Point.builder()
                .id(id)
                .amount(1000L)
                .refreshTime(refreshTime)
                .build();

        pointRedisRepository.save(point);

        Point findPoint = pointRedisRepository.findById(id).get();
        assertAll("기본 등록 조회",
                () -> assertThat(findPoint.getAmount()).isEqualTo(1000L),
                () -> assertThat(findPoint.getRefreshTime()).isEqualTo(refreshTime)
        );
    }
}