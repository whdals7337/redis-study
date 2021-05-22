package com.redis.study.repository;

import org.springframework.data.repository.CrudRepository;
import com.redis.study.domain.entity.Point;

public interface PointRedisRepository extends CrudRepository<Point, Long> {
}
