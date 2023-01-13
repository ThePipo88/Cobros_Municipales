package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Rol;

import java.util.Date;
import java.util.List;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {

    public List<Rol> findByEstado(boolean estado);

    public List<Rol> findByFechaCreacionBetween(Date startDate, Date endDate);

}
