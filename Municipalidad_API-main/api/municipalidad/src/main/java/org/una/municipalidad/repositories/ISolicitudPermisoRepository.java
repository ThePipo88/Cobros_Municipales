package org.una.municipalidad.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.SolicitudPermiso;

import java.util.Date;
import java.util.List;

@Repository
public interface ISolicitudPermisoRepository extends JpaRepository<SolicitudPermiso,Long>{


    public List<SolicitudPermiso> findByFechaCreacionBetween(Date startDate, Date endDate);

    public List<SolicitudPermiso> findByEstado(boolean estado);
}
