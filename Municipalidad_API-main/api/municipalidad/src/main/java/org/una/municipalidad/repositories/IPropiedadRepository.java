package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Excepcion;
import org.una.municipalidad.entities.Propiedad;
import org.una.municipalidad.entities.RutaBus;

import javax.persistence.Column;
import java.util.List;

@Repository
public interface IPropiedadRepository extends JpaRepository<Propiedad, Long> {

    public List<Propiedad> findByProvincia(String provincia);

    public List<Propiedad> findByCanton(String canton);

    public List<Propiedad> findByDistrito(String distrito);

    public List<Propiedad> findByZona(String zona);
}
