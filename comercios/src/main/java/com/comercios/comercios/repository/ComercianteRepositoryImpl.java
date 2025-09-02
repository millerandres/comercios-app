package com.comercios.comercios.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComercianteRepositoryImpl implements ComercianteRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Object[]> obtenerReporteComerciantesActivos() {
        return em.createNativeQuery("SELECT * FROM TABLE(fn_reporte_comerciantes)")
                .getResultList();
    }
}