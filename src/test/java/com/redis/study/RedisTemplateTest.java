package com.redis.study;

import com.redis.study.domain.entity.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest
public class RedisTemplateTest {


    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void Strings_구조_테스트() {
        //given
        String key = "point";
        String id = "wellbell";
        long amount = 2000L;
        LocalDateTime time = LocalDateTime.now();
        Point point = Point.builder()
                .id(id)
                .amount(amount)
                .refreshTime(time)
                .build();

        ValueOperations<String, Point> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set(key, point);

        //when
        Point findPoint = stringStringValueOperations.get(key);

        //then
        assertAll("Strings_구조_테스트",
                () -> assertThat(findPoint.getId()).isEqualTo(id),
                () -> assertThat(findPoint.getAmount()).isEqualTo(amount),
                () -> assertThat(findPoint.getRefreshTime()).isEqualTo(time)
        );
    }

    @Test
    public void testList() {
        //given
        String key = "point";
        String id = "wellbell";
        long amount = 2000L;
        LocalDateTime time = LocalDateTime.now();
        Point point = Point.builder()
                .id(id)
                .amount(amount)
                .refreshTime(time)
                .build();

        ListOperations<String, Point> stringStringListOperations = redisTemplate.opsForList();
        stringStringListOperations.rightPush(key, point);
        stringStringListOperations.rightPush(key, point);
        stringStringListOperations.rightPush(key, point);
        stringStringListOperations.rightPush(key, point);
        stringStringListOperations.rightPush(key, point);

        //when
        List<Point> range = stringStringListOperations.range(key, 0, 9);

        //then
        assertAll(
                () -> {
                    for (Point findPoint : range) {
                        assertAll(
                                () -> assertThat(findPoint.getId()).isEqualTo(id),
                                () -> assertThat(findPoint.getAmount()).isEqualTo(amount),
                                () -> assertThat(findPoint.getRefreshTime()).isEqualTo(time)
                        );
                    }
                }
        );
    }
}
