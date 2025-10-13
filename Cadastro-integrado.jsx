import './Cadastro.css'
import Calendar from './Calendar'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import logo from './assets/shared image.png'
import pacienteImg from './assets/paciente.png'
import pacienteHoverImg from './assets/paciente (1).png'
import psicologiaImg from './assets/psicologia.png'
import psicologiaSelectedImg from './assets/psicologia (1).png'
import axios from 'axios'

// Configuração da API
const api = axios.create({
  baseURL: 'http://localhost:9090'
});

const Cadastro = () => {
  const [userType, setUserType] = useState('')
  const [birthDate, setBirthDate] = useState('')
  const [ageError, setAgeError] = useState('')
  const [email, setEmail] = useState('')
  const [confirmEmail, setConfirmEmail] = useState('')
  const [emailError, setEmailError] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [passwordError, setPasswordError] = useState('')
  const [acceptTerms, setAcceptTerms] = useState(false)
  const navigate = useNavigate();

  const handleBack = () => {
    navigate(-1);
  };

  const handleLogin = () => {
    navigate('/entrar');
  };

  // Função para enviar cadastro
  const enviarCadastro = async (dadosFormulario) => {
    try {
      const response = await api.post('/api/cadastro', dadosFormulario);
      return { sucesso: true, dados: response.data };
    } catch (error) {
      if (error.response) {
        return { sucesso: false, erro: error.response.data };
      } else {
        return { sucesso: false, erro: 'Erro de conexão com o servidor' };
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Coletar dados do formulário
    const formData = new FormData(e.target);
    const dadosFormulario = {
      nomeCompleto: formData.get('nomeCompleto'),
      cpf: formData.get('cpf'),
      telefone: formData.get('telefone'),
      email: email,
      senha: password,
      sexo: formData.get('sexo'),
      tipoUsuario: userType,
      termosAceitos: acceptTerms,
      dataNascimento: birthDate,
      especialidade: userType === 'profissional' ? formData.get('especialidade') : null
    };

    // Enviar para o backend
    const resultado = await enviarCadastro(dadosFormulario);
    
    if (resultado.sucesso) {
      alert('Cadastro realizado com sucesso!');
      // Usar redirecionamento do servidor ou fallback
      if (resultado.dados.redirectTo) {
        navigate(resultado.dados.redirectTo);
      } else if (userType === 'profissional') {
        navigate('/areaprofissional');
      } else {
        navigate('/areapaciente');
      }
    } else {
      alert(typeof resultado.erro === 'string' ? resultado.erro : 'Erro no cadastro');
    }
  };

  // Validações e handlers...
  
  return (
    <div className="cadastro-container">
      <button className="back-btn" onClick={handleBack}>← Voltar</button>
      <div className="content-wrapper">
        <div className="logo-section">
          <img src={logo} alt="ADC Psicologia" />
        </div>
        <div className="cadastro-card">
          <h1>Criar Conta</h1>
          <p>Preencha os dados para se cadastrar</p>
          
          {/* Seleção de tipo de usuário */}
          <div className="user-type-selection">
            <h3>Selecione o tipo de usuário</h3>
            <div className="user-cards">
              <div className={`user-card ${userType === "paciente" ? "selected" : ""}`}
                   onClick={() => setUserType("paciente")}>
                <img src={pacienteImg} alt="Paciente" className="card-image" />
                <h4>Paciente</h4>
              </div>
              <div className={`user-card ${userType === "profissional" ? "selected" : ""}`}
                   onClick={() => setUserType("profissional")}>
                <img src={userType === "profissional" ? psicologiaSelectedImg : psicologiaImg} 
                     alt="Profissional" className="card-image" />
                <h4>Profissional</h4>
              </div>
            </div>
          </div>
          
          {/* Formulário de cadastro */}
          <form className="cadastro-form" onSubmit={handleSubmit}>
            <div className="input-group">
              <input type="text" name="nomeCompleto" placeholder="Nome completo" required />
            </div>
            
            <div className="input-row">
              <div className="input-group">
                <input type="text" name="cpf" placeholder="CPF" required />
              </div>
              <div className="input-group">
                <input type="tel" name="telefone" placeholder="Telefone" required />
              </div>
            </div>

            <div className="input-group">
              <input 
                type="email" 
                value={email} 
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email" 
                required 
              />
            </div>

            <div className="input-group">
              <input 
                type="email" 
                value={confirmEmail} 
                onChange={(e) => setConfirmEmail(e.target.value)}
                placeholder="Confirmar Email" 
                required 
              />
            </div>

            <div className="input-group">
              <input 
                type="password" 
                value={password} 
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Senha" 
                required 
              />
            </div>

            <div className="input-group">
              <input 
                type="password" 
                value={confirmPassword} 
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="Confirmar Senha" 
                required 
              />
            </div>

            <div className="input-group">
              <select name="sexo" required>
                <option value="">Selecione o sexo</option>
                <option value="masculino">Masculino</option>
                <option value="feminino">Feminino</option>
                <option value="outro">Outro</option>
              </select>
            </div>

            <div className="input-group">
              <input 
                type="date" 
                value={birthDate} 
                onChange={(e) => setBirthDate(e.target.value)}
                required 
              />
            </div>

            {userType === 'profissional' && (
              <div className="input-group">
                <input type="text" name="especialidade" placeholder="Especialidade" required />
              </div>
            )}

            <div className="terms-group">
              <input 
                type="checkbox" 
                id="terms" 
                checked={acceptTerms} 
                onChange={(e) => setAcceptTerms(e.target.checked)}
              />
              <label htmlFor="terms">Aceito os termos de uso</label>
            </div>
            
            <button type="submit" className="cadastro-btn" disabled={!acceptTerms || !userType}>
              Criar Conta
            </button>
            <button type="button" className="login-link-btn" onClick={handleLogin}>
              Já tenho conta
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}

export default Cadastro