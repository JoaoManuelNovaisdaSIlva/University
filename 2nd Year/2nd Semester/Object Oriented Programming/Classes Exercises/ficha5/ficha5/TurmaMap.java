import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.Comparator;

/**
 * Write a description of class TurmaMap here.
 * Estrategia de agregacao
 * @author (your name)
 * @version (a version number or a date)
 */
public class TurmaMap
{
    private String nome;
    private String codigoTurma;
    private Map<String, Aluno> alunos;
    
    public TurmaMap(){
        this.nome = "";
        this.codigoTurma = "";
        this.alunos = new HashMap<>();
    }
    // collection e tanto faz uma lista ou um conjunto
    public TurmaMap(String nome, String cod, Collection <Aluno> inscritos){
        this.nome = nome;
        this.codigoTurma = cod;
        this.alunos = new HashMap<>();
        // 1) so porque e agregacao
        // putAll so funciona com map
        //for(Aluno a: inscritos)
        //    this.alunos.put(a.getNumero(),a);
            
        // 2) agregacao com iterador interno
        this.alunos = inscritos.stream().collect(Collectors.toMap(a-> a.getNumero(), a -> a));
        
    }
    // falta construtor de copia
    
    public Map<String,Aluno> getAlunos(){
        Map<String, Aluno> res = new HashMap<>();
        res.putAll(this.alunos);
        return res;
    }
    
    // iterador externo pelos valores
    public Map<String,Aluno> getAlunos1(){
        Map<String, Aluno> res = new HashMap<>();
        
        for(Aluno a: this.alunos.values())
            res.put(a.getNumero(),a);
            
        return res;
    }
    // iterador externo pelas chaves
    // faz sentido quando a chave nao faz parte do objeto apontado
    public Map<String,Aluno> getAlunos2(){
        Map<String, Aluno> res = new HashMap<>();
        
        for(String numero: this.alunos.keySet())
            res.put(numero, this.alunos.get(numero));
            
        return res;
    }
    // iterador externo pelo entrySet
    public Map<String,Aluno> getAlunos3(){
        Map<String, Aluno> res = new HashMap<>();
        
        for(Map.Entry<String, Aluno> e: this.alunos.entrySet())
            res.put(e.getKey(), e.getValue());
            
        return res;
    }
    // iterador interno
    public Map<String,Aluno> getAlunos4(){
        return this.alunos.values().stream().collect(Collectors.toMap(a -> a.getNumero(), a -> a));
    }
    // iterador interno
    public Map<String,Aluno> getAlunos5(){
        return this.alunos.entrySet().stream().collect(Collectors.toMap(a -> a.getKey(), a -> a.getValue()));
    }
    
    // utilizando o contrutor parametrizado de map
    // a estrategia 1 e 6 so servem para agragacao e as de 2 a 5 server para as duas mas necessitao clones
    public Map<String, Aluno> getAlunos6(){
        return new HashMap<String,Aluno>(this.alunos);
    }
    
    // iii)
    public Aluno getAluno(String codAluno){
        if(this.alunos.containsKey(codAluno))
            return this.alunos.get(codAluno);
        else // vamos conseguir  tratar este tipo de situacoes
            return null;
    }
    
    // viii)
    
    public Set<Aluno> alunosordemDecrescenteNumro(){
        Comparator<Aluno> ordemDecres = (a1, a2) -> (a2.getNumero()).compareTo(a1.getNumero());
        
        return this.alunos.values().stream().sorted(ordemDecres).collect(Collectors.toSet());
    }
}
