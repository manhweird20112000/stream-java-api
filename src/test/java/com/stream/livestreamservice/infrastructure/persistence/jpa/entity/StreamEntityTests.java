package com.stream.livestreamservice.infrastructure.persistence.jpa.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class StreamEntityTests {

    @Test
    void streamEntityMapsToStreamTable() throws Exception {
        Class<?> entityType = Class.forName(
                "com.stream.livestreamservice.infrastructure.persistence.jpa.entity.StreamEntity");

        assertThat(hasAnnotation(entityType, "Entity")).isTrue();
        assertThat(annotationValue(entityType, "Table", "name")).isEqualTo("stream");

        Field id = entityType.getDeclaredField("id");
        assertThat(hasAnnotation(id, "Id")).isTrue();
        assertThat(annotationValue(id, "Column", "name")).isEqualTo("id");

        assertColumn(entityType, "title", "title");
        assertColumn(entityType, "description", "description");
        assertColumn(entityType, "status", "status");
        assertColumn(entityType, "streamKey", "stream_key");
        assertColumn(entityType, "playbackUrl", "playback_url");
        assertColumn(entityType, "createdAt", "created_at");
        assertColumn(entityType, "updatedAt", "updated_at");

        Constructor<?> constructor = entityType.getDeclaredConstructor();
        assertThat(Modifier.isProtected(constructor.getModifiers())).isTrue();
    }

    private static void assertColumn(Class<?> type, String fieldName, String columnName) throws Exception {
        Field field = type.getDeclaredField(fieldName);
        assertThat(annotationValue(field, "Column", "name")).isEqualTo(columnName);
    }

    private static boolean hasAnnotation(Class<?> type, String simpleName) {
        return Arrays.stream(type.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getSimpleName().equals(simpleName));
    }

    private static boolean hasAnnotation(Field field, String simpleName) {
        return Arrays.stream(field.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getSimpleName().equals(simpleName));
    }

    private static Object annotationValue(Class<?> type, String simpleName, String methodName) throws Exception {
        Annotation annotation = Arrays.stream(type.getAnnotations())
                .filter(candidate -> candidate.annotationType().getSimpleName().equals(simpleName))
                .findFirst()
                .orElseThrow();
        return annotation.annotationType().getMethod(methodName).invoke(annotation);
    }

    private static Object annotationValue(Field field, String simpleName, String methodName) throws Exception {
        Annotation annotation = Arrays.stream(field.getAnnotations())
                .filter(candidate -> candidate.annotationType().getSimpleName().equals(simpleName))
                .findFirst()
                .orElseThrow();
        return annotation.annotationType().getMethod(methodName).invoke(annotation);
    }
}
