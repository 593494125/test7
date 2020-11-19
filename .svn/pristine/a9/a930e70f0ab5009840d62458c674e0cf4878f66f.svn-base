package com.springboot.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import com.springboot.service.redisdatasource.BaseRedisDatasourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisDBConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseRedisDatasourceService baseRedisDatasourceService;

    // adi数据库连接信息,从配置文件获取
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-active}")
    private String maxActive;
    @Value("${spring.redis.database}")
    private String dbIndex;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private String maxWait;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private String maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private String minIdle;

    public static Map<String,RedisTemplate<String, String>> redisTemplateMap = new HashMap<>();

    @Bean(name = "getRedisTemplate")
    public RedisTemplate<String, String> getRedisTemplate() {
        try {
            return redisTemplateObject();
        } catch (Exception e) {
        }
        return null;
    }
    @PostConstruct
    public void initRedisTemp() throws Exception{
        log.info("###### START 初始化 Redis 连接池 START ######");
        redisTemplateMap.put(SystemConstant.DEFAULT_REDIS_KEY,redisTemplateObject());
        log.info("###### END 初始化 Redis 连接池 END ######");
    }
    @Bean
    public RedisTemplate<String, String> redisTemplateObject() throws Exception {
        RedisTemplate<String, String> redisTemplateObject = new RedisTemplate<String, String>();
        redisTemplateObject.setConnectionFactory(redisConnectionFactory(jedisPoolConfig()));
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }

    /**
     * 连接池配置信息
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));
        poolConfig.setMaxTotal(Integer.parseInt(maxActive));
        //最小空闲连接数
        poolConfig.setMinIdle(Integer.parseInt(minIdle));
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(5);
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        poolConfig.setSoftMinEvictableIdleTimeMillis(60000);
        //当池内没有可用的连接时，最大等待时间
        poolConfig.setMaxWaitMillis(Integer.parseInt(maxWait));

        //------其他属性根据需要自行添加-------------
        return poolConfig;
    }

    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(host);
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(dbIndex));
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(port);
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }
    //序列化
    private void setSerializer(RedisTemplate<String, String> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //在使用String的数据结构的时候使用这个来更改序列化方式
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer );
        template.setValueSerializer(stringSerializer );
        template.setHashKeySerializer(stringSerializer );
        template.setHashValueSerializer(stringSerializer );
    }

    /**
     * 根据sixCode或者id 获取对应的redisTemplate实例
     * @return
     */
    public RedisTemplate<String, String> getRedisTemplateById(String datasource){
//        String datasource = DBContextHolder.getDataSource();
            RedisTemplate<String, String> redisTemplate=null;
        if (!StringUtils.isEmpty(datasource)) {
            Map<String,RedisTemplate<String, String>> dynamicTargetDataSources2 = this.redisTemplateMap;
            if (dynamicTargetDataSources2.containsKey(datasource)) {
//                log.info("---当前redis数据源：" + datasource + "---"+"不需要重新创建");
                redisTemplate=redisTemplateMap.get(datasource);
                if(redisTemplate==null){
//                    log.info("不存在数据源,准备创建redis数据源");
                    //从数据库中查询redis数据源
                    String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+datasource);
//                log.info("缓存中该企业orgId是：---"+orgId+"---");
                    if(StringUtils.isNotEmpty(orgId)){
                        String redisId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+orgId);
                        if(StringUtils.isNotEmpty(redisId)){
                            BaseRedisDatasource baseRedisDatasource=null;
                            String jsonBaseRedisDatasource=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId+"_baseRedisDatasource");
                            if(StringUtils.isNotEmpty(jsonBaseRedisDatasource)){
                                baseRedisDatasource= JSONObject.parseObject(jsonBaseRedisDatasource, BaseRedisDatasource.class);
                            }else{
                                baseRedisDatasource=baseRedisDatasourceService.findById("",redisId);
                                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseRedisDatasource.getOrgId()+"_baseRedisDatasource", JSON.toJSON(baseRedisDatasource).toString());
                            }
                            if(baseRedisDatasource!=null){
                                redisTemplate=createRedisTemplate(baseRedisDatasource);
                                redisTemplateMap.put(datasource,redisTemplate);
                                log.info("成功创建redis数据源:"+datasource);
//                            log.info("---当前redis数据源是：" + baseRedisDatasource.getHost() + "---");
                                redisTemplate= redisTemplateMap.get(datasource);
                            }else{
//                            log.info("数据库中不存在该redis---"+datasource+"---数据源!");
                                return null;
                            }
                        }else{
//                        log.info("根据orgId---"+orgId+"---查找缓存中不存在redisId!");
                        }
                    }else{
//                    log.info("缓存中不存在orgId,原因是认证失败!");
                    }
                }
            } else {
//                log.info("不存在数据源,准备创建redis数据源");
                //从数据库中查询redis数据源
                String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+datasource);
//                log.info("缓存中该企业orgId是：---"+orgId+"---");
                if(StringUtils.isNotEmpty(orgId)){
                    String redisId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"redisId_"+orgId);
                    if(StringUtils.isNotEmpty(redisId)){
                        BaseRedisDatasource baseRedisDatasource=null;
                        String jsonBaseRedisDatasource=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId+"_baseRedisDatasource");
                        if(StringUtils.isNotEmpty(jsonBaseRedisDatasource)){
                            baseRedisDatasource= JSONObject.parseObject(jsonBaseRedisDatasource, BaseRedisDatasource.class);
                        }else{
                            baseRedisDatasource=baseRedisDatasourceService.findById("",redisId);
                            redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,baseRedisDatasource.getOrgId()+"_baseRedisDatasource", JSON.toJSON(baseRedisDatasource).toString());
                        }
                        if(baseRedisDatasource!=null){
                            redisTemplate=createRedisTemplate(baseRedisDatasource);
                            redisTemplateMap.put(datasource,redisTemplate);
                            log.info("成功创建redis数据源:"+datasource);
//                            log.info("---当前redis数据源是：" + baseRedisDatasource.getHost() + "---");
                            redisTemplate= redisTemplateMap.get(datasource);
                        }else{
//                            log.info("数据库中不存在该redis---"+datasource+"---数据源!");
                            return null;
                        }
                    }else{
//                        log.info("根据orgId---"+orgId+"---查找缓存中不存在redisId!");
                    }
                }else{
//                    log.info("缓存中不存在orgId,原因是认证失败!");
                }
            }
        } else {
//            log.info("---当前数据源：默认redis数据源:"+ datasource + "---");
            redisTemplate=redisTemplateMap.get(datasource);
        }

        return redisTemplate;
    }

    public RedisTemplate<String, String> createRedisTemplate(BaseRedisDatasource baseRedisDatasource) {
        try {
            log.info("首次创建redis地址={}",baseRedisDatasource.getHost()+":"+baseRedisDatasource.getPort()+","+baseRedisDatasource.getDbIndex()+"号库");
            return createRedisTemplateObject(baseRedisDatasource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public RedisTemplate<String, String> createRedisTemplateObject(BaseRedisDatasource baseRedisDatasource) throws Exception {
        RedisTemplate<String, String> redisTemplateObject = new RedisTemplate<String, String>();
        redisTemplateObject.setConnectionFactory(createRedisConnectionFactory(jedisPoolConfig(),baseRedisDatasource));
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }
    //切换的时候根据id获取redisTemplate实例
    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @return
     */
    public RedisConnectionFactory createRedisConnectionFactory(JedisPoolConfig jedisPoolConfig, BaseRedisDatasource baseRedisDatasource) {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName(baseRedisDatasource.getHost());
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(baseRedisDatasource.getDbIndex()));
        //设置密码
        redisStandaloneConfiguration.setPassword(RedisPassword.of(baseRedisDatasource.getPassword()));
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(Integer.parseInt(baseRedisDatasource.getPort()));
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器
        jpcb.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }




}
