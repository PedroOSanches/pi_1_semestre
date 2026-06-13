package br.maua.domain;

public class Resposta {
    private int idResposta;
    private Tentativa tentativa;
    private Questao questao;
    private Double nota;

    public int getIdResposta() { 
        return idResposta; 
    }
    
    public void setIdResposta(int idResposta) { 
        this.idResposta = idResposta; 
    }

    public Tentativa getTentativa() { 
        return tentativa; 
    }
    public void setTentativa(Tentativa tentativa) { 
        this.tentativa = tentativa; 
    }

    public Questao getQuestao() { 
        return questao; 
    }
    public void setQuestao(Questao questao) { 
        this.questao = questao; 
    }

    public Double getNota() { 
        return nota; 
    }
    public void setNota(Double nota) { 
        this.nota = nota; 
    }

}

