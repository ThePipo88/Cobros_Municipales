package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.CobroCancelado;
import org.una.municipalidad.entities.CobroGenerado;

import java.util.Date;
import java.util.List;

@Repository
public interface ICobroCanceladoRepository extends JpaRepository<CobroCancelado, Long> {

    public List<CobroCancelado> findByFechaCreacionBetween(Date startDate, Date endDate);

    public List<CobroCancelado> findByFechaCreacion(Date startDate);

    @Query(value = "SELECT u FROM CobroCancelado u WHERE u.cobroGenerado.contribuyenteServicio.contribuyente.cedula = :cedula AND u.fechaCreacion >= :startDate AND u.fechaCreacion <= :endDate")
    public List<CobroCancelado> findByCobroBetweenCedulaAndFecha(@Param("cedula")String cedula, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("SELECT u FROM CobroCancelado u WHERE u.fechaCreacion >= :startDate AND u.fechaCreacion <= :endDate")
    public List<CobroCancelado> findByCobroBetweenFecha(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
