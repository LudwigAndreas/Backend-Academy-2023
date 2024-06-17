package ru.edu.homework.springcrud.db.annotation;

import org.springframework.stereotype.Component;
import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.exception.db.DataSourceException;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

@Component
public class AnnotationProcessor {

    private AnnotationProcessor() {}
    public static <T, ID> boolean isUnique(T object, Map<ID, T> map) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field: fields) {
            if (field.isAnnotationPresent(UniqueField.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if ((value == null)) {
                        return false;
                    }
                    for (T object1 : map.values()) {
                        if (!Objects.equals(getIdFieldValue(object1), getIdFieldValue(object)) && field.get(object1).equals(value)) {
                            return false;
                        }
                    }
                } catch (IllegalAccessException e) {
                    return false;
                }
            }
        }
        return true;
    }


    public static  <T, ID> ID getIdFieldValue(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(IdField.class)) {
                field.setAccessible(true);
                try {
                    return (ID) field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new DataSourceException("Error while reading file");
                }
            }
        }
        throw new DataSourceException("No id field found");
    }
}
