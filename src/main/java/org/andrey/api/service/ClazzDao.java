package org.andrey.api.service;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.andrey.api.exception.ApiNotFoundException;
import org.andrey.api.persistence.model.Clazz;
import org.andrey.api.rest.bean.ClazzDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class ClazzDao extends EntityDao<ClazzDto, Clazz> {

    @PersistenceContext
    private EntityManager entityManager;

    public ClazzDao() {
    }

    public Long create(ClazzDto clazzDto) {

        Clazz clazz = new Clazz();
        clazz.setTitle(clazzDto.getTitle());
        clazz.setDescription(clazzDto.getDescription());
        entityManager.persist(clazz);
        return clazz.getCode();
    }

    public Long update(ClazzDto clazzDto) {

        Clazz clazz = get(clazzDto.getCode());
        if (clazz != null){

            if (clazzDto.getTitle() != null){

                clazz.setTitle(clazzDto.getTitle());
            }

            if (clazzDto.getDescription() != null){

                clazz.setDescription(clazzDto.getDescription());
            }
            return clazz.getCode();
        }

        return null;
    }

    @Override
    public void remove(Long id) {

        Clazz clazz = get(id);
        entityManager.remove(clazz);
    }


    public List<ClazzDto> getAll() {

        List<Clazz> persistedUsers = entityManager.createQuery("from Clazz").getResultList();

        Iterable transform = Iterables.transform(persistedUsers, new Function<Clazz, ClazzDto>() {
            @Override
            public ClazzDto apply(Clazz clazz) {

                return new ClazzDto(clazz);
            }
        });
        List<ClazzDto> classesToReturn = Lists.<ClazzDto>newArrayList(transform);

        return classesToReturn;
    }

    @Override
    public Clazz get(Long code) {

        Query query = entityManager.createQuery("from Clazz where code = :code").setParameter("code", code);
        List<Clazz> clazzes = (List<Clazz>) query.getResultList();
        if (clazzes.isEmpty()){

            throw new ApiNotFoundException("Class with code " + code + " does not exist");
        }
        return clazzes.get(0);
    }

    public List<ClazzDto> searchByString(String searchString) {

        String queryString = "from Clazz where (title like '%" + searchString + "%' OR description like '%" + searchString + "%')";
        Query query = entityManager.createQuery(queryString);
        List<Clazz> persistedClasses = query.getResultList();

        Iterable<ClazzDto> transform = Iterables.transform(persistedClasses, new Function<Clazz, ClazzDto>() {
            @Override
            public ClazzDto apply(Clazz clazz) {

                return new ClazzDto(clazz);
            }
        });
        List<ClazzDto> classesToReturn = Lists.<ClazzDto>newArrayList(transform);

        return classesToReturn;
    }
}
