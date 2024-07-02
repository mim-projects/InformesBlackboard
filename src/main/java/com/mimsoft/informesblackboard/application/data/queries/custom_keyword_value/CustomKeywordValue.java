package com.mimsoft.informesblackboard.application.data.queries.custom_keyword_value;

import jakarta.persistence.*;

@Entity
@SqlResultSetMapping(
        name = "CustomKeywordValue",
        classes = @ConstructorResult(
                targetClass = CustomKeywordValue.class,
                columns = {
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "keyword", type = String.class),
                        @ColumnResult(name = "value", type = Float.class)
                }
        )
)
public class CustomKeywordValue {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "value")
    private Float value;

    public CustomKeywordValue() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
