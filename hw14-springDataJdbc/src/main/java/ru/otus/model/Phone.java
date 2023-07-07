package ru.otus.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("phone")
@Getter
@NoArgsConstructor
public class Phone{
    @Id
    private Long id;

    @Nonnull
    private String phoneNumber;

    @PersistenceCreator
    public Phone(Long id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Phone(String phoneNumber) {
        this(null, phoneNumber);
    }
}
