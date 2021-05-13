package club.yeyue.maven.util;

import club.yeyue.maven.mysql.demo.ClubLongEntity;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Jackson json工具类
 *
 * @author fred
 * @date 2021-05-13 17:02
 */
@Slf4j
public class JacksonUtils {
    private JacksonUtils() {
    }

    private final static ObjectMapper JACKSON_OBJECT_MAPPER = new ObjectMapper();
    private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    static {
        JACKSON_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_PATTERN));
        JACKSON_OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JACKSON_OBJECT_MAPPER.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        JACKSON_OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
        );
        JACKSON_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //处理LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        //处理LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        //处理LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        JACKSON_OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    public static <T> String toJsonString(T object) {
        return toJsonString(object, JACKSON_OBJECT_MAPPER);
    }

    public static <T> String toJsonString(T object, ObjectMapper mapper) {
        if (object == null) {
            return null;
        }
        try {
            return object instanceof String ? (String) object : mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error ", e);
            return null;
        }
    }

    public static <T> String toJsonStringFormat(T object) {
        return toJsonStringFormat(object, JACKSON_OBJECT_MAPPER);
    }

    public static <T> String toJsonStringFormat(T object, ObjectMapper mapper) {
        if (object == null) {
            return null;
        }
        try {
            return object instanceof String ? (String) object : mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Parse object to string error", e);
            return null;
        }
    }

    public static <T> T toObject(String string, Class<T> clazz) {
        return toObject(string, clazz, JACKSON_OBJECT_MAPPER);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(String string, Class<T> clazz, ObjectMapper mapper) {
        if (StringUtils.isEmpty(string) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) string : mapper.readValue(string, clazz);
        } catch (Exception e) {
            log.warn("Parse String to object error!", e);
            return null;
        }
    }

    public static <T> T toObject(String string, TypeReference<T> typeReference) {
        return toObject(string, typeReference, JACKSON_OBJECT_MAPPER);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toObject(String string, TypeReference<T> typeReference, ObjectMapper mapper) {
        if (StringUtils.isEmpty(string) || typeReference == null) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T) string : mapper.readValue(string, typeReference);

        } catch (Exception e) {
            log.warn("Parse String to object error!", e);
            return null;
        }
    }


//    public static void main(String[] args) {
//        ClubLongEntity entity = new ClubLongEntity();
//        entity.setId(100L);
//        entity.setCreated(LocalDateTime.now());
//        entity.setClubName("夜月");
//        ClubLongEntity entity2 = new ClubLongEntity();
//        entity2.setId(120L);
//        entity2.setUpdated(LocalDateTime.now());
//        entity2.setClubName("夜月v2");
//        // 测试对象转JSON
//        System.out.println(toJsonString(""));
//        String json = toJsonString(entity);
//        System.out.println(json);
//        List<ClubLongEntity> entityList = Collections.singletonList(entity2);
//        String json2 = toJsonString(entityList);
//        System.out.println(json2);
//        Map<String, List<ClubLongEntity>> map = Collections.singletonMap("ces", entityList);
//        String json3 = toJsonString(map);
//        System.out.println(json3);
//        ClubLongEntity entity4 = toObject(json, ClubLongEntity.class);
//        System.out.println(entity4);
//        List<ClubLongEntity> entityList2 = toObject(json2, new TypeReference<List<ClubLongEntity>>() {
//        });
//        System.out.println(entityList2);
//        System.out.println(toObject("[]", new TypeReference<List<ClubLongEntity>>() {
//        }));
//        System.out.println(toObject("{}", ClubLongEntity.class));
//    }
}
