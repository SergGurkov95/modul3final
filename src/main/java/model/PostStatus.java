package model;

public enum PostStatus {
    //опубликованн
    PUBLISHED,
    //создан, но еще не был на проверке
    DRAFT,
    //возвращен на доработку после модерации
    WORKSHEET,
    //на модерации
    IN_PROGRESS
}
