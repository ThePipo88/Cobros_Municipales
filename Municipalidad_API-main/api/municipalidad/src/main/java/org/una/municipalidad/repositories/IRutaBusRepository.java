package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.entities.Servicio;
import java.util.List;

@Repository
public interface IRutaBusRepository extends JpaRepository<RutaBus, Long> {

    public List<RutaBus> findByNombre(String nombre);

    public List<RutaBus> findByInicio(String inicio);

    public List<RutaBus> findByFin(String fin);

    @Query(value = "SELECT u FROM RutaBus u WHERE u.servicio.id = :id")
    public List<RutaBus> findByServicioId(Long id);
}
