package com.in2dm.adc.tcc.Controller;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.entity.Profissional;
import com.in2dm.adc.tcc.model.services.UsuarioService;
import com.in2dm.adc.tcc.model.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String senha = credentials.get("senha");
            String tipoUsuario = credentials.get("tipoUsuario");

            if (email == null || senha == null || tipoUsuario == null) {
                return ResponseEntity.badRequest().body("Email, senha e tipo de usuário são obrigatórios");
            }

            if ("usuario".equals(tipoUsuario)) {
                return loginUsuario(email, senha);
            } else if ("profissional".equals(tipoUsuario)) {
                return loginProfissional(email, senha);
            } else {
                return ResponseEntity.badRequest().body("Tipo de usuário inválido");
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor");
        }
    }

    private ResponseEntity<?> loginUsuario(String email, String senha) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        
        if (!usuario.getSenha().equals(senha)) {
            return ResponseEntity.badRequest().body("Senha incorreta");
        }

        return ResponseEntity.ok(Map.of(
            "message", "Login realizado com sucesso",
            "id", usuario.getId(),
            "nome", usuario.getNomeCompleto(),
            "email", usuario.getEmail(),
            "tipo", "usuario",
            "redirectTo", "/usuario/dashboard"
        ));
    }

    private ResponseEntity<?> loginProfissional(String email, String senha) {
        Profissional profissional = profissionalService.buscarPorEmail(email);
        
        if (profissional == null) {
            return ResponseEntity.badRequest().body("Profissional não encontrado");
        }
        
        if (!profissional.getSenha().equals(senha)) {
            return ResponseEntity.badRequest().body("Senha incorreta");
        }

        return ResponseEntity.ok(Map.of(
            "message", "Login realizado com sucesso",
            "id", profissional.getId(),
            "nome", profissional.getNomeCompleto(),
            "email", profissional.getEmail(),
            "especialidade", profissional.getEspecialidade(),
            "tipo", "profissional",
            "redirectTo", "/profissional/dashboard"
        ));
    }
}