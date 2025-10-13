package com.in2dm.adc.tcc.Controller;

import com.in2dm.adc.tcc.model.entity.Profissional;
import com.in2dm.adc.tcc.model.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/profissionais")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalController(ProfissionalService profissionalService) {
        this.profissionalService = profissionalService;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProfissional(@RequestBody Profissional profissional) {
        try {
            validarProfissional(profissional);
            
            // Verificar se email já existe
            if (profissionalService.emailExiste(profissional.getEmail())) {
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }

            // Verificar se CPF já existe
            if (profissionalService.cpfExiste(profissional.getCpf())) {
                return ResponseEntity.badRequest().body("CPF já cadastrado");
            }
            
            Profissional profissionalSalvo = profissionalService.salvar(profissional);
            return ResponseEntity.status(HttpStatus.CREATED).body(profissionalSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar profissional.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Profissional>> listarTodosProfissionais() {
        List<Profissional> profissionais = profissionalService.listarTodos();
        return ResponseEntity.ok(profissionais);
    }

    private void validarProfissional(Profissional profissional) {
        if (profissional == null) {
            throw new IllegalArgumentException("Dados do profissional não fornecidos.");
        }

        if (profissional.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória.");
        }

        int idade = calcularIdade(profissional.getDataNascimento());
        if (idade < 18) {
            throw new IllegalArgumentException("Você deve ter pelo menos 18 anos para se cadastrar como profissional.");
        }

        if (!Boolean.TRUE.equals(profissional.isTermosAceitos())) {
            throw new IllegalArgumentException("Você deve aceitar os termos de uso.");
        }

        if (isNullOrEmpty(profissional.getEmail()) ||
                isNullOrEmpty(profissional.getSenha()) ||
                isNullOrEmpty(profissional.getCpf()) ||
                isNullOrEmpty(profissional.getNomeCompleto())) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos.");
        }
    }

    private int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private boolean isNullOrEmpty(String value) {
        return Objects.isNull(value) || value.trim().isEmpty();
    }
}

