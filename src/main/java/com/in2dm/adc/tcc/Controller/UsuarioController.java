package com.in2dm.adc.tcc.Controller;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            // Validações básicas
            if (usuario.getNomeCompleto() == null || usuario.getEmail() == null || 
                usuario.getSenha() == null || usuario.getCpf() == null) {
                return ResponseEntity.badRequest().body("Campos obrigatórios não preenchidos");
            }

            // Verificar se email já existe
            if (usuarioService.emailExiste(usuario.getEmail())) {
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }

            // Verificar se CPF já existe
            if (usuarioService.cpfExiste(usuario.getCpf())) {
                return ResponseEntity.badRequest().body("CPF já cadastrado");
            }

            // Validação de idade
            if (usuario.getDataNascimento() != null && calcularIdade(usuario.getDataNascimento()) < 12) {
                return ResponseEntity.badRequest().body("Idade mínima: 12 anos");
            }

            // Validação de termos
            if (!usuario.isTermosAceitos()) {
                return ResponseEntity.badRequest().body("Aceite os termos de uso");
            }

            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }



    @GetMapping
    public ResponseEntity <List<Usuario>> listarTodosUsuario () {
        return ResponseEntity.ok().body(usuarioService.listarTodos());
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}


