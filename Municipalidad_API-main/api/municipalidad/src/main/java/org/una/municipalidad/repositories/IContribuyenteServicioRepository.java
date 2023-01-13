package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.dto.ContribuyenteServicioDTO;
import org.una.municipalidad.entities.ContribuyenteServicio;


import java.util.List;
import java.util.Optional;

@Repository
public interface IContribuyenteServicioRepository extends JpaRepository<ContribuyenteServicio, Long> {

    public List<ContribuyenteServicio> findByPorcentaje(float porcentaje);

}
