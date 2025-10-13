package com.in2dm.adc.tcc.Controller;

import com.in2dm.adc.tcc.model.entity.Usuario;
import com.in2dm.adc.tcc.model.entity.Profissional;
import com.in2dm.adc.tcc.model.services.UsuarioService;
import com.in2dm.adc.tcc.model.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

@RestController
@RequestMapping("/api/cadastro")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Map<String, Object> dados) {
        try {
            // Validações básicas
            String nomeCompleto = (String) dados.get("nomeCompleto");
            String email = (String) dados.get("email");
            String senha = (String) dados.get("senha");
            String cpf = (String) dados.get("cpf");
            String tipoUsuario = (String) dados.get("tipoUsuario");
            Boolean termosAceitos = (Boolean) dados.get("termosAceitos");

            if (nomeCompleto == null || email == null || senha == null || cpf == null || tipoUsuario == null) {
                return ResponseEntity.badRequest().body("Campos obrigatórios não preenchidos");
            }

            if (termosAceitos == null || !termosAceitos) {
                return ResponseEntity.badRequest().body("Aceite os termos de uso");
            }

            // Verificar duplicatas em ambas as tabelas
            if (usuarioService.emailExiste(email) || profissionalService.emailExiste(email)) {
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }

            if (usuarioService.cpfExiste(cpf) || profissionalService.cpfExiste(cpf)) {
                return ResponseEntity.badRequest().body("CPF já cadastrado");
            }

            // Validar idade se fornecida
            LocalDate dataNascimento = null;
            if (dados.get("dataNascimento") != null) {
                dataNascimento = LocalDate.parse((String) dados.get("dataNascimento"));
                int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
                
                if ("profissional".equals(tipoUsuario) && idade < 18) {
                    return ResponseEntity.badRequest().body("Profissionais devem ter pelo menos 18 anos");
                } else if ("usuario".equals(tipoUsuario) && idade < 12) {
                    return ResponseEntity.badRequest().body("Usuários devem ter pelo menos 12 anos");
                }
            }

            // Criar entidade baseada no tipo
            if ("profissional".equals(tipoUsuario)) {
                return cadastrarProfissional(dados, nomeCompleto, email, senha, cpf, termosAceitos, dataNascimento);
            } else {
                return cadastrarUsuario(dados, nomeCompleto, email, senha, cpf, termosAceitos, dataNascimento);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    private ResponseEntity<?> cadastrarUsuario(Map<String, Object> dados, String nomeCompleto, 
                                             String email, String senha, String cpf, 
                                             Boolean termosAceitos, LocalDate dataNascimento) {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(nomeCompleto);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setCpf(cpf);
        usuario.setTipoUsuario("paciente");
        usuario.setTermosAceitos(termosAceitos);
        usuario.setDataNascimento(dataNascimento);
        
        if (dados.get("telefone") != null) {
            usuario.setTelefone((String) dados.get("telefone"));
        }
        if (dados.get("sexo") != null) {
            usuario.setSexo((String) dados.get("sexo"));
        }

        Usuario usuarioSalvo = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Usuário cadastrado com sucesso",
            "id", usuarioSalvo.getId(),
            "tipo", "usuario",
            "redirectTo", "/usuario/dashboard"
        ));
    }
    
    private ResponseEntity<?> cadastrarProfissional(Map<String, Object> dados, String nomeCompleto, 
                                                   String email, String senha, String cpf, 
                                                   Boolean termosAceitos, LocalDate dataNascimento) {
        Profissional profissional = new Profissional();
        profissional.setNomeCompleto(nomeCompleto);
        profissional.setEmail(email);
        profissional.setSenha(senha);
        profissional.setCpf(cpf);
        profissional.setTermosAceitos(termosAceitos);
        profissional.setDataNascimento(dataNascimento);
        
        if (dados.get("telefone") != null) {
            profissional.setTelefone((String) dados.get("telefone"));
        }
        if (dados.get("sexo") != null) {
            profissional.setSexo((String) dados.get("sexo"));
        }
        if (dados.get("especialidade") != null) {
            profissional.setEspecialidade((String) dados.get("especialidade"));
        }

        Profissional profissionalSalvo = profissionalService.salvar(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "message", "Profissional cadastrado com sucesso",
            "id", profissionalSalvo.getId(),
            "tipo", "profissional",
            "redirectTo", "/profissional/dashboard"
        ));
    }
}