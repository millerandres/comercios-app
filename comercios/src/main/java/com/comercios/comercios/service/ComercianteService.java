package com.comercios.comercios.service;

import com.comercios.comercios.dto.ComercianteRequest;
import com.comercios.comercios.entity.Comerciante;
import com.comercios.comercios.repository.ComercianteRepository;
import com.comercios.comercios.specification.ComercianteSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComercianteService {

    private final ComercianteRepository comercianteRepository;

    public ComercianteService(ComercianteRepository comercianteRepository) {
        this.comercianteRepository = comercianteRepository;
    }

    @Cacheable("municipios") // Cache opcional
    public List<String> obtenerMunicipios() {
        return comercianteRepository.obtenerMunicipiosUnicos();
    }

    public Page<Comerciante> listarPaginado(String nombre, LocalDate fechaRegistro, String estado, Pageable pageable) {
        Specification<Comerciante> spec = ComercianteSpecification.filtrar(nombre, fechaRegistro, estado);
        return comercianteRepository.findAll(spec, pageable);
    }

    public Comerciante crear(ComercianteRequest request, String usuario) {
        Comerciante c = new Comerciante();
        mapearDto(c, request, usuario);
        c.setFechaRegistro(request.getFechaRegistro());
        return comercianteRepository.save(c);
    }

    public Comerciante actualizar(Long id, ComercianteRequest request, String usuario) {
        Comerciante c = comercianteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No encontrado"));
        mapearDto(c, request, usuario);
        return comercianteRepository.save(c);
    }

    public void eliminar(Long id) {
        comercianteRepository.deleteById(id);
    }

    public Comerciante cambiarEstado(Long id, String estado, String usuario) {
        Comerciante c = comercianteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No encontrado"));
        c.setEstado(estado);
        c.setFechaActualizacion(LocalDateTime.now());
        c.setUsuarioActualizacion(usuario);
        return comercianteRepository.save(c);
    }
    public Comerciante obtenerPorId(Long id) {
        return comercianteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comerciante no encontrado con ID: " + id));
    }

    private void mapearDto(Comerciante c, ComercianteRequest r, String usuario) {
        c.setNombre(r.getNombre());
        c.setMunicipio(r.getMunicipio());
        c.setTelefono(r.getTelefono());
        c.setCorreo(r.getCorreo());
        c.setEstado(r.getEstado());
        c.setFechaActualizacion(LocalDateTime.now());
        c.setUsuarioActualizacion(usuario);
    }
    public byte[] generarCsvComerciantesActivosDesdeFuncion() {
        List<Object[]> filas = comercianteRepository.obtenerReporteComerciantesActivos();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre|Municipio|Teléfono|Correo|FechaRegistro|Estado|CantidadEstablecimientos|TotalIngresos|CantidadEmpleados\n");

        for (Object[] fila : filas) {
            for (int i = 0; i < fila.length; i++) {
                csvBuilder.append(fila[i] != null ? fila[i].toString() : "");
                if (i < fila.length - 1) csvBuilder.append("|");
            }
            csvBuilder.append("\n");
        }

        return csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
