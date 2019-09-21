package com.auzmor.sample.sms.persistence.repo;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CacheRepository {

	@Autowired
	private StringRedisTemplate template;

	public void store(String key, String value) {
		template.opsForValue().set(key, value, 4, TimeUnit.HOURS);
	}

	public boolean exists(String key) {
		return null != template.opsForValue().get(key);
	}

	public long getCounter(String key) {
		return Optional.ofNullable(template.opsForValue().get(key)).map(v -> Long.valueOf(v)).orElse(0l);
	}

	public void incrementCounter(String key) {
		template.opsForValue().setIfAbsent(key, "0", 24, TimeUnit.HOURS);
		template.opsForValue().increment(key);
	}
}
