package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.RutaBus;
import org.una.municipalidad.entities.TipoDerecho;

import java.util.List;

@Repository
public interface ITipoDerechoRepository extends JpaRepository<TipoDerecho, Long>{

    public List<TipoDerecho> findByTipo(String tipo);

    public List<TipoDerecho> findByMonto(int monto);

    @Query(value = "SELECT u FROM TipoDerecho u WHERE u.cementerio.id = :id")
    public List<TipoDerecho> findByServicioId(Long id);
}
