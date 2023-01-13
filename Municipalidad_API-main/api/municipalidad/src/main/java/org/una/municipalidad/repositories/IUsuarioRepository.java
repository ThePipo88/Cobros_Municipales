package org.una.municipalidad.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.una.municipalidad.entities.Usuario;


import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByCedulaAndPasswordEncriptado(String cedula, String passwordEncriptado);

    public Usuario findByCedula(String cedula);

    public List<Usuario> findByCedulaContaining(String cedula);

    public List<Usuario> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);

    @Query("select u from Usuario u where UPPER(u.nombreCompleto) like CONCAT('%',UPPER(:nombreCompleto),'%')")
    public Usuario findNombreCompletoWithLikeSQL(@Param("nombreCompleto")String nombreCompleto);

}
