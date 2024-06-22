package com.mimsoft.boilerplate.domain.core;

import java.io.Serializable;

public abstract class EntityCore implements Serializable {
    public abstract Integer getId();

    public abstract void setId(Integer id);
}
