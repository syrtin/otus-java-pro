package ru.otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var is = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            assert is != null;
            var reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            var gson = new Gson();
            var listType = new TypeToken<List<Measurement>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}