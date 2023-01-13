package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Cementerio;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.entities.Servicio;
import java.util.List;

@Repository
public interface ICementerioRepository extends JpaRepository<Cementerio, Long>{

    public List<Cementerio> findBySector(String sector);

    public List<Cementerio> findByOcupado(String ocupado);

    @Query(value = "SELECT u FROM Cementerio u WHERE u.servicio.id = :id")
    public List<Cementerio> findByServicioId(Long id);
}

