package com.comercios.comercios.repository;

import com.comercios.comercios.entity.Comerciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComercianteRepository extends JpaRepository<Comerciante, Long> {

    @Query("SELECT DISTINCT c.municipio FROM Comerciante c")
    List<String> obtenerMunicipiosUnicos();
}
