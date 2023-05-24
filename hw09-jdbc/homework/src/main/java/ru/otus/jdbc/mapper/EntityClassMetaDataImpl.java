package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final String name;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> nonIdFields;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        name = entityClass.getSimpleName().toLowerCase();

        allFields = Arrays.stream(entityClass.getDeclaredFields())
                .collect(Collectors.toList());

        idField = allFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .get();

        nonIdFields = allFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());

        try {
            int fieldsAmount = allFields.size();
            Class<?>[] classTypes = new Class<?>[fieldsAmount];
            for (int i = 0; i < fieldsAmount; i++) {
                classTypes[i] = allFields.get(i).getType();
            }
            constructor = entityClass.getConstructor(classTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return nonIdFields;
    }
}