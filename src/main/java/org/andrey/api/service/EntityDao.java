package org.andrey.api.service;

import java.util.Collection;

public abstract class EntityDao<DTO, PersistenceEntity> {

    public abstract Long create(DTO dto);

    public abstract void remove(Long id);

    public abstract Long update(DTO dto);

    public abstract Collection<DTO> getAll();

    public abstract PersistenceEntity get(Long id);

    public Collection<DTO> getAll(Long startAt, Long maxResult){

        throw new UnsupportedOperationException("Paginated listing is not supported yet! Will be added later when developers have time for that");
    };
}
