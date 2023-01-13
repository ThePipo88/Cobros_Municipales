package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Contribuyente;


import java.util.Date;
import java.util.List;

@Repository
public interface IContribuyenteRepository extends JpaRepository<Contribuyente, Long> {

    public List<Contribuyente> findByIdContaining(String id);

    public List<Contribuyente> findByNombreContainingIgnoreCase(String nombre);

    public List<Contribuyente> findByFechaNacimiento(Date fechaNacimiento);

    public List<Contribuyente> findByDireccionContainingIgnoreCase(String direccion);

    public List<Contribuyente> findByCorreoElectronicoContainingIgnoreCase(String correoElectronico);

    public List<Contribuyente> findByTelefonoContainingIgnoreCase(String telefono);

    public Contribuyente findByCedula(String cedula);


}
