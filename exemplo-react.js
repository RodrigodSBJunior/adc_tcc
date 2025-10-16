// Exemplo de como conectar o React com sua API

// 1. CADASTRO - Enviar dados para o backend
const cadastrarUsuario = async (dadosUsuario) => {
  try {
    const response = await fetch('http://localhost:9090/api/cadastro', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        nomeCompleto: dadosUsuario.nome,
        email: dadosUsuario.email,
        senha: dadosUsuario.senha,
        cpf: dadosUsuario.cpf,
        telefone: dadosUsuario.telefone,
        sexo: dadosUsuario.sexo,
        tipoUsuario: dadosUsuario.tipo, // "usuario" ou "profissional"
        especialidade: dadosUsuario.especialidade, // só para profissional
        termosAceitos: true,
        dataNascimento: dadosUsuario.dataNascimento
      })
    });

    const resultado = await response.json();
    
    if (response.ok) {
      alert('Cadastro realizado com sucesso!');
      // Redirecionar ou fazer login automático
    } else {
      alert('Erro: ' + resultado);
    }
  } catch (error) {
    alert('Erro de conexão: ' + error.message);
  }
};

// 2. LOGIN - Verificar credenciais
const fazerLogin = async (email, senha, tipoUsuario) => {
  try {
    const response = await fetch('http://localhost:9090/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email,
        senha: senha,
        tipoUsuario: tipoUsuario // "usuario" ou "profissional"
      })
    });

    const resultado = await response.json();
    
    if (response.ok) {
      // Salvar dados do usuário logado
      localStorage.setItem('usuario', JSON.stringify(resultado));
      alert('Login realizado com sucesso!');
      // Redirecionar para dashboard
    } else {
      alert('Erro: ' + resultado);
    }
  } catch (error) {
    alert('Erro de conexão: ' + error.message);
  }
};

// 3. LISTAR USUÁRIOS - Para verificar se cadastrou
const listarUsuarios = async () => {
  try {
    const response = await fetch('http://localhost:9090/api/teste/usuarios');
    const usuarios = await response.json();
    console.log('Usuários cadastrados:', usuarios);
    return usuarios;
  } catch (error) {
    console.error('Erro ao listar usuários:', error);
  }
};

// Exemplo de uso no componente React:
/*
function CadastroForm() {
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    senha: '',
    cpf: '',
    telefone: '',
    sexo: '',
    tipo: 'usuario',
    dataNascimento: ''
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    cadastrarUsuario(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
      // seus campos do formulário
    </form>
  );
}
*/