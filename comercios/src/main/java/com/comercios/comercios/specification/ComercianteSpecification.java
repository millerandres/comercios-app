package com.comercios.comercios.specification;

import com.comercios.comercios.entity.Comerciante;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ComercianteSpecification {

    public static Specification<Comerciante> filtrar(String nombre, LocalDate fechaRegistro, String estado) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
            }
            if (fechaRegistro != null) {
                predicates.add(cb.equal(root.get("fechaRegistro"), fechaRegistro));
            }
            if (estado != null && !estado.isBlank()) {
                predicates.add(cb.equal(root.get("estado"), estado));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}