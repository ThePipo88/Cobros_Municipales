package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.CobroGenerado;
import org.una.municipalidad.entities.Contribuyente;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface ICobroGeneradoRepository extends JpaRepository<CobroGenerado, Long> {

    @Query(value = "SELECT u FROM CobroGenerado u")
    public List<CobroGenerado> findByBusquedaId(@Param("id")Long id);

   // Optional<CobroGenerado> dateProcedure(@Param("fecha_in") Date fecha);

    public List<CobroGenerado> findByMonto(Double monto);

    //@Query(value="{call COBROS_MENSUAL(:fecha_in)}",nativeQuery = true)
    //public Optional<CobroGenerado> findByFechaCobro(@Param("fecha_in")Date fechaCobro);

    @Query(value="{call COBROS_RUTABUS(:fecha_in,:fecha_out)}",nativeQuery = true)
    public Optional<CobroGenerado> findByCobroRutaBus(@Param("fecha_in")Date startDate,@Param("fecha_out")Date endDate);

    @Query(value="{call COBROS_LIMPIEZAPARQUES(:fecha_in,:fecha_out)}",nativeQuery = true)
    public Optional<CobroGenerado> findByCobroLimpiezaParque(@Param("fecha_in")Date startDate,@Param("fecha_out")Date endDate);

    @Query(value="{call COBROS_CEMENTERIO(:fecha_in,:fecha_out)}",nativeQuery = true)
    public Optional<CobroGenerado> findByCobroCementerio(@Param("fecha_in")Date startDate,@Param("fecha_out")Date endDate);

    public List<CobroGenerado> findByFechaCobroBetween(Date startDate, Date endDate);

    public List<CobroGenerado> findByEstado(boolean estado);

    @Query(value = "SELECT u FROM CobroGenerado u LEFT JOIN u.contribuyenteServicio e WHERE e.contribuyente.cedula = :cedula AND e.servicio.tipoServicio = :tipo AND u.estado = 1")
    public List<CobroGenerado> findCobroByCedulaAndTipo(@Param("cedula") String cedula,@Param("tipo") String tipo);

    @Query(value = "SELECT u FROM CobroGenerado u LEFT JOIN u.contribuyenteServicio e WHERE e.contribuyente.cedula = :cedula AND u.estado = 1")
    public List<CobroGenerado> findCobroByCedula(@Param("cedula") String cedula);

    @Query(value = "SELECT u FROM CobroGenerado u LEFT JOIN u.contribuyenteServicio e WHERE e.servicio.id = :id")
    public List<CobroGenerado> findCobroByServicioId(@Param("id")Long id);

}
