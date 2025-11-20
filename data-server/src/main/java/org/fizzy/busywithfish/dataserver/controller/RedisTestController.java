package org.fizzy.busywithfish.dataserver.controller;

import org.fizzy.busywithfish.dataserver.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/string")
    public String setString(@RequestParam String key, @RequestParam String value) {
        redisUtil.set(key, value);
        return "Key '" + key + "' set to '" + value + "'";
    }

    @GetMapping("/string")
    public String getString(@RequestParam String key) {
        Object value = redisUtil.get(key);
        return value != null ? value.toString() : "Key not found";
    }

    @PostMapping("/string/expire")
    public String setStringWithExpire(@RequestParam String key, @RequestParam String value, @RequestParam long time) {
        redisUtil.set(key, value, time);
        return "Key '" + key + "' set to '" + value + "' with expiration " + time + " seconds";
    }

    @PostMapping("/hash")
    public String setHash(@RequestParam String key, @RequestParam String field, @RequestParam String value) {
        redisUtil.hset(key, field, value);
        return "Hash field '" + field + "' set to '" + value + "' in key '" + key + "'";
    }

    @GetMapping("/hash")
    public Object getHash(@RequestParam String key, @RequestParam String field) {
        return redisUtil.hget(key, field);
    }

    @PostMapping("/hash/map")
    public String setHashMap(@RequestParam String key, @RequestBody Map<String, Object> map) {
        redisUtil.hmset(key, map);
        return "Hash map set in key '" + key + "'";
    }

    @GetMapping("/hash/map")
    public Map<Object, Object> getHashMap(@RequestParam String key) {
        return redisUtil.hmget(key);
    }

    @DeleteMapping("/key")
    public String deleteKey(@RequestParam String... key) {
        redisUtil.del(key);
        return "Keys deleted";
    }

    @GetMapping("/expire")
    public long getExpire(@RequestParam String key) {
        return redisUtil.getExpire(key);
    }

    @PostMapping("/expire")
    public boolean setExpire(@RequestParam String key, @RequestParam long time) {
        return redisUtil.expire(key, time);
    }
}