package jaeseok.numble.mybox.common.redis;

import jaeseok.numble.mybox.util.annotation.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@DisplayName("RedisTemplate 학습 테스트")
@IntegrationTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @DisplayName("opsForValue()는 String type value를 return")
    @Test
    void testOpsForValue() {
        // given
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String key = "string_key";
        String value = "string_value";

        valueOperations.set(key, value);

        // when
        String result = valueOperations.get(key).toString();

        // then
        Assertions.assertEquals(value, result);
    }

    @DisplayName("opsForList()는 List type value를 return")
    @Test
    void testOpsForList() {
        // given
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        String listKey = "list_key";

        List<Object> list = List.of("1", "2", "3");

        // when
        for (Object object : list) {
            listOperations.leftPush(listKey, object);
        }

        // then
        for (Object object : list) {
            Object result = listOperations.rightPop(listKey);
            Assertions.assertEquals(object, result);
        }
    }

    @DisplayName("opsForSet()는 Set type value를 return")
    @Test
    void testOpsForSet() {
        // given
        SetOperations setOperations = redisTemplate.opsForSet();
        String setKey = "set_key";
        Object[] values = {"h", "e", "l", "l", "o"};

        // when
        setOperations.add(setKey, values);

        // then
        Set<Object> resultSet = setOperations.members(setKey);
        for (Object object : values) {
            Assertions.assertTrue(resultSet.contains(object));
        }
    }

    @DisplayName("opsForHash()는 Map type value를 return")
    @Test
    void testOpsForHash() {
        // given
        HashOperations hashOperations = redisTemplate.opsForHash();
        String hashKey = "hash_key";

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");

        hashOperations.putAll(hashKey, map);

        // when
        Map<Object, Object> result = hashOperations.entries(hashKey);

        //then
        for (Map.Entry entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            Assertions.assertEquals(value, result.get(key));
        }
    }
}
