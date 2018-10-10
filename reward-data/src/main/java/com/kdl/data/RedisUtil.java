package com.kdl.data;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.collect.Lists;
import com.kdl.common.framework.http.ApplicationContextUtil;

/**
 * Created by zhy on 2018/1/13.
 */
public class RedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	private static final RedisTemplate<String, Object> redisTemplate = ApplicationContextUtil.getBean("redisTemplate");

	private static final String REDIS_CODE = "utf-8";

	private static final String PREFIX_KEY = "fostom";
	private static final String LINK_STR = ":";

	/**
	 * 验证码默认有效期 120 秒
	 */
	public static final Long VERIFY_EXPIRED_TIME = 60 * 2L;

	/**
	 * session 有效期 7 天
	 */
	public static final Long SESSION_EXPIRED_TIME = 60 * 60 * 24 * 7L;

	/**
	 * 默认的redis 有效期 1 天
	 */
	public static final Long DEFAULT_COMMON_EXPIRED_TIME = 60 * 60 * 24L;

	/**
	 * redis 锁 最大有效期
	 */
	public static final Long DEFAULT_LOCK_EXPIRED_TIME = 5 * 60 * 1000L;

	/**
	 * 生产RedisKey
	 * 
	 * @param keys
	 * @return
	 */
	public static String generateKey(String... keys) {
		StringBuffer sf = new StringBuffer();
		if (keys != null && keys.length > 0) {
			sf.append(PREFIX_KEY).append(LINK_STR);
			for (int i = 0, size = keys.length; i < size; i++) {
				sf.append(keys[i]);
				if (i < size - 1) {
					sf.append(LINK_STR);
				}
			}
		}
		return sf.toString();
	}

	/**
	 * 更新key 有效期
	 * 
	 * @param key
	 * @param time
	 *            时间为 秒
	 * @return
	 */
	public static boolean expireKey(final String key, final Long time) {
		return redisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 删除指定Keys，
	 * 
	 * @param keys
	 * @return 删除失败的keys
	 */
	public static List<String> delKey(final String... keys) {
		return redisTemplate.execute(new RedisCallback<List<String>>() {
			@Override
			public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
				List<String> failKeys = Lists.newArrayList();
				for (int i = 0; i < keys.length; i++) {
					long no = connection.del(keys[i].getBytes());
					if (no == 0) {
						failKeys.add(keys[i]);
					}

				}
				return failKeys;
			}
		});
	}

	/**
	 * 判断redis数据库是否有对应的key
	 */
	public static boolean exist(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	/**
	 * 设置String 类型 key value
	 * 
	 * @param key
	 *            redis key
	 * @param value
	 *            redis value
	 * @param time
	 *            有效期 缺省值 1 天
	 * @return
	 */
	public static boolean setString(final String key, final String value, final Long time) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key.getBytes(), value.getBytes());
				Long timeExpire = time;
				if (time == null) {
					timeExpire = DEFAULT_COMMON_EXPIRED_TIME;
				}
				return connection.expire(key.getBytes(), timeExpire);
			}
		});
	}

	/**
	 * 根据key取出对应值
	 * 
	 * @param key
	 *            redis key
	 * @return
	 */
	public static String getStrinValue(final String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					byte[] bs = connection.get(key.getBytes());
					if (bs == null) {
						return null;
					}
					return new String(connection.get(key.getBytes()), REDIS_CODE);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("getStrinValue error,key:{}", key);
				}
				return "";
			}
		});
	}

	/**
	 * redis 加锁， 锁自身业务时效为5分钟，需要手动调用unLock()方法来释放锁<br>
	 * 
	 * @param key
	 */
	public static boolean lock(final String key) {
		Long currentTime = System.currentTimeMillis();
		Long value = currentTime + DEFAULT_LOCK_EXPIRED_TIME;
		Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, value);
		if (lock) {// 设置锁成功
			// expireKey(key, 2 * DEFAULT_LOCK_EXPIRED_TIME);
			return true;
		}
		// 已经存在锁，设置锁失败
		Long oldExpireTime = (Long) redisTemplate.opsForValue().get(key);
		// 过期，可重新设置锁
		currentTime = System.currentTimeMillis();
		if (oldExpireTime < currentTime) {
			Long newExpireTime = currentTime + DEFAULT_LOCK_EXPIRED_TIME;
			Long currentExpireTime = (Long) redisTemplate.opsForValue().getAndSet(key, newExpireTime);
			if (currentExpireTime.longValue() == oldExpireTime.longValue()) {
				// expireKey(key, 2 * DEFAULT_LOCK_EXPIRED_TIME);
				return true;
			}
		}
		return false;
	}

	/**
	 * 解锁即删除当前Key
	 * 
	 * @param key
	 */
	public static void unLock(final String key) {
		redisTemplate.delete(key);
	}

}
