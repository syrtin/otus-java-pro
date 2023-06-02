package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;


    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                return entityClassMetaData.getConstructor().newInstance(getInitArgs(rs));
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var resultList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    resultList.add(entityClassMetaData.getConstructor().newInstance(getInitArgs(rs)));
                }
                return resultList;
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException | SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            List<Object> objectFieldsWithoutIdValues = new ArrayList<>();
            List<Field> fieldsWithoutIdValues = entityClassMetaData.getFieldsWithoutId();
            for (Field field : fieldsWithoutIdValues) {
                field.setAccessible(true);
                objectFieldsWithoutIdValues.add(field.get(object));
            }
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    objectFieldsWithoutIdValues);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            Field idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            Field nameField = entityClassMetaData.getFieldsWithoutId().get(0);
            nameField.setAccessible(true);

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    List.of(nameField.get(object), idField.get(object)));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private Object[] getInitArgs(ResultSet rs) throws SQLException {
        Object[] initArgs = new Object[rs.getMetaData().getColumnCount()];
        if (rs.next()) {
            for (int i = 0; i < initArgs.length; i++) {
                initArgs[i] = rs.getObject(i + 1);
            }
        }
        return initArgs;
    }
}