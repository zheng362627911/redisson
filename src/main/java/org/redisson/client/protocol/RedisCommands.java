/**
 * Copyright 2014 Nikita Koksharov, Nickolay Borbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.client.protocol;

import java.util.List;
import java.util.Map;

import org.redisson.client.protocol.RedisCommand.ValueType;
import org.redisson.client.protocol.decoder.KeyValueObjectDecoder;
import org.redisson.client.protocol.decoder.ListScanResult;
import org.redisson.client.protocol.decoder.ListScanResultReplayDecoder;
import org.redisson.client.protocol.decoder.MapScanResult;
import org.redisson.client.protocol.decoder.MapScanResultReplayDecoder;
import org.redisson.client.protocol.decoder.NestedMultiDecoder;
import org.redisson.client.protocol.decoder.ObjectListReplayDecoder;
import org.redisson.client.protocol.decoder.ObjectMapReplayDecoder;
import org.redisson.client.protocol.decoder.StringDataDecoder;
import org.redisson.client.protocol.decoder.StringListReplayDecoder;
import org.redisson.client.protocol.decoder.StringMapReplayDecoder;
import org.redisson.client.protocol.decoder.StringReplayDecoder;
import org.redisson.client.protocol.pubsub.PubSubStatusDecoder;
import org.redisson.client.protocol.pubsub.PubSubStatusMessage;

public interface RedisCommands {

    RedisStrictCommand<Boolean> UNWATCH = new RedisStrictCommand<Boolean>("UNWATCH", new BooleanReplayConvertor());
    RedisStrictCommand<Boolean> WATCH = new RedisStrictCommand<Boolean>("WATCH", new BooleanReplayConvertor());
    RedisStrictCommand<Boolean> MULTI = new RedisStrictCommand<Boolean>("MULTI", new BooleanReplayConvertor());
    RedisCommand<List<Object>> EXEC = new RedisCommand<List<Object>>("EXEC", new ObjectListReplayDecoder());

    RedisCommand<Long> SREM = new RedisCommand<Long>("SREM", 2, ValueType.OBJECTS);
    RedisCommand<Long> SADD = new RedisCommand<Long>("SADD", 2, ValueType.OBJECTS);
    RedisCommand<Boolean> SADD_SINGLE = new RedisCommand<Boolean>("SADD", new BooleanReplayConvertor(), 2);
    RedisCommand<Boolean> SREM_SINGLE = new RedisCommand<Boolean>("SREM", new BooleanReplayConvertor(), 2);
    RedisCommand<List<Object>> SMEMBERS = new RedisCommand<List<Object>>("SMEMBERS", new ObjectListReplayDecoder());
    RedisCommand<ListScanResult<Object>> SSCAN = new RedisCommand<ListScanResult<Object>>("SSCAN", new NestedMultiDecoder(new ObjectListReplayDecoder(), new ListScanResultReplayDecoder()), ValueType.MAP);
    RedisCommand<Boolean> SISMEMBER = new RedisCommand<Boolean>("SISMEMBER", new BooleanReplayConvertor(), 2);
    RedisStrictCommand<Long> SCARD = new RedisStrictCommand<Long>("SCARD");

    RedisCommand<Object> LPOP = new RedisCommand<Object>("LPOP");
    RedisCommand<Long> LREM = new RedisCommand<Long>("LREM", 3);
    RedisCommand<Object> LINDEX = new RedisCommand<Object>("LINDEX");
    RedisCommand<Object> LINSERT = new RedisCommand<Object>("LINSERT", 3, ValueType.OBJECTS);
    RedisStrictCommand<Long> LLEN = new RedisStrictCommand<Long>("LLEN");
    RedisStrictCommand<Boolean> LTRIM = new RedisStrictCommand<Boolean>("LTRIM", new BooleanReplayConvertor());

    RedisStrictCommand<Boolean> EXPIRE = new RedisStrictCommand<Boolean>("EXPIRE", new BooleanReplayConvertor());
    RedisStrictCommand<Boolean> EXPIREAT = new RedisStrictCommand<Boolean>("EXPIREAT", new BooleanReplayConvertor());
    RedisStrictCommand<Boolean> PERSIST = new RedisStrictCommand<Boolean>("PERSIST", new BooleanReplayConvertor());
    RedisStrictCommand<Long> TTL = new RedisStrictCommand<Long>("TTL");

    RedisCommand<Object> RPOPLPUSH = new RedisCommand<Object>("RPOPLPUSH");
    RedisCommand<Object> BRPOPLPUSH = new RedisCommand<Object>("BRPOPLPUSH");
    RedisCommand<Object> BLPOP = new RedisCommand<Object>("BLPOP", new KeyValueObjectDecoder());

    RedisCommand<Boolean> PFADD = new RedisCommand<Boolean>("PFADD", new BooleanReplayConvertor(), 2);
    RedisCommand<Long> PFCOUNT = new RedisCommand<Long>("PFCOUNT");
    RedisStrictCommand<String> PFMERGE = new RedisStrictCommand<String>("PFMERGE", new StringReplayDecoder());

    RedisCommand<Long> RPOP = new RedisCommand<Long>("RPOP");
    RedisCommand<Long> LPUSH = new RedisCommand<Long>("LPUSH");
    RedisCommand<List<Object>> LRANGE = new RedisCommand<List<Object>>("LRANGE", new ObjectListReplayDecoder());
    RedisCommand<Long> RPUSH = new RedisCommand<Long>("RPUSH", 2, ValueType.OBJECTS);

    RedisStrictCommand<String> SCRIPT_LOAD = new RedisStrictCommand<String>("SCRIPT", "LOAD", new StringDataDecoder());
    RedisStrictCommand<Boolean> SCRIPT_KILL = new RedisStrictCommand<Boolean>("SCRIPT", "KILL", new BooleanReplayConvertor());
    RedisStrictCommand<Boolean> SCRIPT_FLUSH = new RedisStrictCommand<Boolean>("SCRIPT", "FLUSH", new BooleanReplayConvertor());
    RedisStrictCommand<List<Object>> SCRIPT_EXISTS = new RedisStrictCommand<List<Object>>("SCRIPT", "EXISTS", new ObjectListReplayDecoder(), new BooleanReplayConvertor());

    RedisStrictCommand<Boolean> EVAL_BOOLEAN = new RedisStrictCommand<Boolean>("EVAL", new BooleanReplayConvertor());
    RedisStrictCommand<String> EVAL_STRING = new RedisStrictCommand<String>("EVAL", new StringReplayDecoder());
    RedisStrictCommand<Long> EVAL_INTEGER = new RedisStrictCommand<Long>("EVAL");
    RedisCommand<List<Object>> EVAL_LIST = new RedisCommand<List<Object>>("EVAL", new ObjectListReplayDecoder());
    RedisCommand<Object> EVAL_OBJECT = new RedisCommand<Object>("EVAL");
    RedisCommand<Object> EVAL_MAP_VALUE = new RedisCommand<Object>("EVAL", ValueType.MAP_VALUE);
    RedisCommand<List<Object>> EVAL_MAP_VALUE_LIST = new RedisCommand<List<Object>>("EVAL", new ObjectListReplayDecoder(), ValueType.MAP_VALUE);

    RedisStrictCommand<Long> INCR = new RedisStrictCommand<Long>("INCR");
    RedisStrictCommand<Long> INCRBY = new RedisStrictCommand<Long>("INCRBY");
    RedisStrictCommand<Long> DECR = new RedisStrictCommand<Long>("DECR");

    RedisStrictCommand<String> AUTH = new RedisStrictCommand<String>("AUTH", new StringReplayDecoder());
    RedisStrictCommand<String> SELECT = new RedisStrictCommand<String>("SELECT", new StringReplayDecoder());
    RedisStrictCommand<Boolean> CLIENT_SETNAME = new RedisStrictCommand<Boolean>("CLIENT", "SETNAME", new BooleanReplayConvertor());
    RedisStrictCommand<String> CLIENT_GETNAME = new RedisStrictCommand<String>("CLIENT", "GETNAME", new StringDataDecoder());
    RedisStrictCommand<String> FLUSHDB = new RedisStrictCommand<String>("FLUSHDB");

    RedisStrictCommand<List<String>> KEYS = new RedisStrictCommand<List<String>>("KEYS", new StringListReplayDecoder());

    RedisCommand<Boolean> HSET = new RedisCommand<Boolean>("HSET", new BooleanReplayConvertor(), 2, ValueType.MAP);
    RedisStrictCommand<String> HINCRBYFLOAT = new RedisStrictCommand<String>("HINCRBYFLOAT");
    RedisCommand<MapScanResult<Object, Object>> HSCAN = new RedisCommand<MapScanResult<Object, Object>>("HSCAN", new NestedMultiDecoder(new ObjectMapReplayDecoder(), new MapScanResultReplayDecoder()), ValueType.MAP);
    RedisCommand<Map<Object, Object>> HGETALL = new RedisCommand<Map<Object, Object>>("HGETALL", new ObjectMapReplayDecoder(), ValueType.MAP);
    RedisCommand<List<Object>> HVALS = new RedisCommand<List<Object>>("HVALS", new ObjectListReplayDecoder(), ValueType.MAP_VALUE);
    RedisCommand<Boolean> HEXISTS = new RedisCommand<Boolean>("HEXISTS", new BooleanReplayConvertor(), 2, ValueType.MAP_KEY);
    RedisStrictCommand<Long> HLEN = new RedisStrictCommand<Long>("HLEN");
    RedisCommand<List<Object>> HKEYS = new RedisCommand<List<Object>>("HKEYS", new ObjectListReplayDecoder(), ValueType.MAP_KEY);
    RedisCommand<String> HMSET = new RedisCommand<String>("HMSET", new StringReplayDecoder(), 1, ValueType.MAP);
    RedisCommand<List<Object>> HMGET = new RedisCommand<List<Object>>("HMGET", new ObjectListReplayDecoder(), 2, ValueType.MAP_KEY, ValueType.MAP_VALUE);
    RedisCommand<Object> HGET = new RedisCommand<Object>("HGET", 2, ValueType.MAP_KEY, ValueType.MAP_VALUE);
    RedisCommand<Long> HDEL = new RedisStrictCommand<Long>("HDEL", 2, ValueType.MAP_KEY);

    RedisStrictCommand<Boolean> DEL_SINGLE = new RedisStrictCommand<Boolean>("DEL", new BooleanReplayConvertor());

    RedisCommand<Object> GET = new RedisCommand<Object>("GET");
    RedisCommand<String> SET = new RedisCommand<String>("SET", 2);
    RedisCommand<Boolean> SETNX = new RedisCommand<Boolean>("SETNX", new BooleanReplayConvertor(), 2);
    RedisCommand<Boolean> SETEX = new RedisCommand<Boolean>("SETEX", new BooleanReplayConvertor(), 2);
    RedisStrictCommand<Boolean> EXISTS = new RedisStrictCommand<Boolean>("EXISTS", new BooleanReplayConvertor());

    RedisStrictCommand<Boolean> RENAMENX = new RedisStrictCommand<Boolean>("RENAMENX", new BooleanReplayConvertor());
    RedisStrictCommand<Boolean> RENAME = new RedisStrictCommand<Boolean>("RENAME", new BooleanReplayConvertor());

    RedisCommand<Long> PUBLISH = new RedisCommand<Long>("PUBLISH", 2);

    RedisStrictCommand<PubSubStatusMessage> SUBSCRIBE = new RedisStrictCommand<PubSubStatusMessage>("SUBSCRIBE", new PubSubStatusDecoder());
    RedisStrictCommand<PubSubStatusMessage> UNSUBSCRIBE = new RedisStrictCommand<PubSubStatusMessage>("UNSUBSCRIBE", new PubSubStatusDecoder());
    RedisStrictCommand<PubSubStatusMessage> PSUBSCRIBE = new RedisStrictCommand<PubSubStatusMessage>("PSUBSCRIBE", new PubSubStatusDecoder());
    RedisStrictCommand<PubSubStatusMessage> PUNSUBSCRIBE = new RedisStrictCommand<PubSubStatusMessage>("PUNSUBSCRIBE", new PubSubStatusDecoder());

    RedisStrictCommand<String> CLUSTER_NODES = new RedisStrictCommand<String>("CLUSTER", "NODES", new StringDataDecoder());

    RedisStrictCommand<List<String>> SENTINEL_GET_MASTER_ADDR_BY_NAME = new RedisStrictCommand<List<String>>("SENTINEL", "GET-MASTER-ADDR-BY-NAME", new StringListReplayDecoder());
    RedisStrictCommand<List<Map<String, String>>> SENTINEL_SLAVES = new RedisStrictCommand<List<Map<String, String>>>("SENTINEL", "SLAVES", new StringMapReplayDecoder());

}