package com.in2dm.adc.tcc.Controller;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.entity.Profissional;
import com.in2dm.adc.tcc.model.services.UsuarioService;
import com.in2dm.adc.tcc.model.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class DashboardController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> dashboardUsuario(@PathVariable Long id) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
            
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Usuario usuario = usuarioOpt.get();
            
            return ResponseEntity.ok(Map.of(
                "id", usuario.getId(),
                "nome", usuario.getNomeCompleto(),
                "email", usuario.getEmail(),
                "telefone", usuario.getTelefone() != null ? usuario.getTelefone() : "",
                "tipo", "usuario",
                "message", "Bem-vindo ao seu painel de usuário!"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao carregar dashboard do usuário");
        }
    }

    @GetMapping("/profissional/{id}")
    public ResponseEntity<?> dashboardProfissional(@PathVariable Long id) {
        try {
            Optional<Profissional> profissionalOpt = profissionalService.buscarPorId(id);
            
            if (profissionalOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Profissional profissional = profissionalOpt.get();
            
            return ResponseEntity.ok(Map.of(
                "id", profissional.getId(),
                "nome", profissional.getNomeCompleto(),
                "email", profissional.getEmail(),
                "telefone", profissional.getTelefone() != null ? profissional.getTelefone() : "",
                "especialidade", profissional.getEspecialidade() != null ? profissional.getEspecialidade() : "",
                "tipo", "profissional",
                "message", "Bem-vindo ao seu painel profissional!"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao carregar dashboard do profissional");
        }
    }
}