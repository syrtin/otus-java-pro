package ru.otus;

import lombok.Getter;

public enum Banknote {
    R5000(5000),
    R2000(2000),
    R1000(1000),
    R500(500),
    R200(200),
    R100(100);

    @Getter
    final int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }
}