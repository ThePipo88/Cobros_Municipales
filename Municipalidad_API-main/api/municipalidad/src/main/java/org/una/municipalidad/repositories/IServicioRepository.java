package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Servicio;

import java.util.Date;
import java.util.List;

@Repository
public interface IServicioRepository extends JpaRepository<Servicio, Long> {

    public List<Servicio> findByTipoServicio(String tipoServicio);

    public List<Servicio> findByEstado(boolean estado);

    public List<Servicio> findByFechaRegistro(Date fechaRegistro);

    public List<Servicio> findByUltimaActualizacion(Date ultimaActualizacion);

}
