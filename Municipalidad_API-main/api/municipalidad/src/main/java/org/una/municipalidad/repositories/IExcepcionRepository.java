package org.una.municipalidad.repositories;

import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Excepcion;
import org.una.municipalidad.entities.Propiedad;
import org.una.municipalidad.entities.Usuario;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Repository
public interface IExcepcionRepository extends JpaRepository<Excepcion, Long> {

    public List<Excepcion> findByUsuario(Long id);

    public List<Excepcion> findByEstado(boolean estado);

    public List<Excepcion> findByFechaCreacion(Date startDate);

}
