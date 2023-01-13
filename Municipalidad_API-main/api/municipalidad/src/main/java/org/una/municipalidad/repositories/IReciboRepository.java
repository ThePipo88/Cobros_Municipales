package org.una.municipalidad.repositories;

import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Propiedad;
import org.una.municipalidad.entities.Recibo;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public interface IReciboRepository extends JpaRepository<Recibo, Long> {

    public List<Recibo> findByContribuyente(String contribuyente);

    public List<Recibo> findByFechaEmision(Date fechaEmision);

    @Query("SELECT u FROM Recibo u WHERE u.fechaEmision >= :startDate AND u.fechaEmision <= :endDate")
    public List<Recibo> findByReciboBetweenFecha(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

}
