package com.mimsoft.informesblackboard.domain.core;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.io.Serializable;
import java.lang.annotation.Annotation;

@Dependent
@RepositoryClass
public class Repository<T extends EntityCore> extends RepositoryCore<T> implements Serializable {
    @Inject
    public Repository(InjectionPoint clazz) {
        setClazz(extractValue(clazz));
    }

    private Class<T> extractValue(InjectionPoint ip) {
        for (Annotation annotation : ip.getQualifiers()) {
            if (annotation.annotationType().equals(RepositoryClass.class))
                return (Class<T>) ((RepositoryClass) annotation).value();
        }
        throw new IllegalStateException("No @Initialized on InjectionPoint");
    }
}
