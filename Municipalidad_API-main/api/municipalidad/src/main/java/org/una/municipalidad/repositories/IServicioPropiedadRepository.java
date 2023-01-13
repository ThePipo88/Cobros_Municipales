package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.dto.ServicioPropiedadDTO;
import org.una.municipalidad.entities.Propiedad;
import org.una.municipalidad.entities.Servicio;
import org.una.municipalidad.entities.ServicioPropiedad;

import java.util.List;

@Repository
public interface IServicioPropiedadRepository extends JpaRepository<ServicioPropiedad, Long> {

    @Query(value = "SELECT u FROM ServicioPropiedad u WHERE u.servicio.id = :id")
    public List<ServicioPropiedad> findByServicioId(@Param("id")Long id);

    @Query(value = "SELECT u FROM ServicioPropiedad u WHERE u.propiedad.id = :id")
    public List<ServicioPropiedad> findByPropiedadId(@Param("id")Long id);
}
