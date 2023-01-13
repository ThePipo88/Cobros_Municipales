package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.ListaSalida;
import org.una.municipalidad.entities.TipoDerecho;

import java.util.List;

@Repository
public interface IListaSalidaRepository extends JpaRepository<ListaSalida, Long> {


    public List<ListaSalida> findByDia(String dia);

    public List<ListaSalida> findByCantidad(int cantidad);

    @Query(value = "SELECT u FROM ListaSalida u WHERE u.rutaBus.id = :id")
    public List<ListaSalida> findByRutaId(Long id);
}
