package com.mick8569.springhub.dao;

import com.mick8569.springhub.models.entities.AbstractEntity;

public class AbstractDao<T extends AbstractEntity> extends AbstractGenericDao<T, Long> {
}
