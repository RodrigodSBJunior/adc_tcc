package com.in2dm.adc.tcc.Controller;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/teste")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class TesteController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/usuario-teste")
    public ResponseEntity<?> criarUsuarioTeste() {
        try {
            Usuario usuario = new Usuario();
            usuario.setNomeCompleto("Teste Usuario");
            usuario.setEmail("teste@email.com");
            usuario.setSenha("123456");
            usuario.setCpf("11111111111");
            usuario.setTipoUsuario("paciente");
            usuario.setTermosAceitos(true);
            usuario.setDataNascimento(LocalDate.of(1990, 1, 1));
            
            Usuario salvo = usuarioService.salvar(usuario);
            return ResponseEntity.ok("Usuario teste criado com ID: " + salvo.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}