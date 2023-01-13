package org.una.municipalidad.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.una.municipalidad.dto.RolDTO;
import org.una.municipalidad.dto.UsuarioDTO;
import org.una.municipalidad.services.IRolService;
import org.una.municipalidad.services.IUsuarioService;

import java.util.Optional;

@Component
public class DataLoader implements ApplicationRunner {

    @Value("${app.admin-user}")
    private String cedula;

    @Value("${app.password}")
    private String password;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(usuarioService.findByCedulaAproximate(cedula).get().size() == 0){

            System.out.println("Se metio aqui");

            Optional<RolDTO> gestorRol = Optional.ofNullable(rolService.create(RolDTO.builder().nombre(RolesTypes.ROLE_GESTOR.name()).build()));
            Optional<RolDTO> gerenteRol = Optional.ofNullable(rolService.create(RolDTO.builder().nombre(RolesTypes.ROLE_GERENTE.name()).build()));
            Optional<RolDTO> auditorRol = Optional.ofNullable(rolService.create(RolDTO.builder().nombre(RolesTypes.ROLE_AUDITOR.name()).build()));
            Optional<RolDTO> administradorRol = Optional.ofNullable(rolService.create(RolDTO.builder().nombre(RolesTypes.ROLE_ADMINISTRADOR.name()).build()));
            Optional<RolDTO> usuarioTelegramRol= Optional.ofNullable(rolService.create(RolDTO.builder().nombre(RolesTypes.ROLE_TELEGRAM.name()).build()));

            UsuarioDTO gestorUsuario = UsuarioDTO.builder()
                    .cedula("0123456789")
                    .nombreCompleto("Usuario Prueba Gestor")
                    .passwordEncriptado("Una2021")
                    .rol(gestorRol.orElseThrow()).build();
            usuarioService.create(gestorUsuario);

            UsuarioDTO contadorUsuario = UsuarioDTO.builder()
                    .cedula("9876543210")
                    .nombreCompleto("Usuario Prueba Gerente")
                    .passwordEncriptado("Una2021")
                    .rol(gerenteRol.orElseThrow()).build();
            usuarioService.create(contadorUsuario);

            UsuarioDTO auditorUsuario = UsuarioDTO.builder()
                    .cedula("192837465")
                    .nombreCompleto("Usuario Prueba Auditor")
                    .passwordEncriptado("Una2021")
                    .rol(auditorRol.orElseThrow()).build();
            usuarioService.create(auditorUsuario);

            UsuarioDTO telegramUsuario = UsuarioDTO.builder()
                    .cedula("telegram")
                    .nombreCompleto("Usuario Telegram")
                    .passwordEncriptado("Una2021")
                    .rol(usuarioTelegramRol.orElseThrow()).build();
            usuarioService.create(telegramUsuario);

            UsuarioDTO administradorUsuario = UsuarioDTO.builder()
                    .cedula(cedula)
                    .nombreCompleto("Usuario Administrador")
                    .passwordEncriptado(password)
                    .rol(administradorRol.orElseThrow()).build();
            usuarioService.create(administradorUsuario);

            System.out.println("Se agrega el usuario inicial a la aplicación");
        }else{
            System.out.println("Usuario existente");
        }
    }
}
