import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class videoYT here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class videoYT
{
    private String nome;
    private List<Byte> bytes;
    private LocalDate dataCarregamento;
    private String resolucao;
    private String duracao;
    private List<String> comentarios;
    private int likes;
    private int dislikes;
    
    public videoYT(){
        this.nome = "";
        this.bytes = new ArrayList<>();
        this.dataCarregamento = LocalDate.now();
        this.resolucao = "";
        this.duracao = "";
        this.comentarios = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
    }
    
    public videoYT(String nome, List<Byte> bytes, LocalDate data, String resolucao, String duracao,
    List<String> comentarios, int likes, int dislikes){
        this.nome = nome;
        this.bytes = bytes;
        this.dataCarregamento = data;
        this.resolucao = resolucao;
        this.duracao = duracao;
        this.comentarios = comentarios;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    
    public videoYT(videoYT e){
        this.nome = e.getNome();
        this.bytes = e.getBytes();
        this.dataCarregamento = e.getData();
        this.resolucao = e.getResolucao();
        this.duracao = e.getDuracao();
        this.comentarios = e.getComentarios();
        this.likes = e.getLikes();
        this.dislikes = e.getDislikes();
    }
    
    
    // gets
    
    public String getNome(){
        return this.nome;
    }
    
    public List<Byte> getBytes(){
        List<Byte> res = new ArrayList<>();
        for(Byte le : bytes){
            res.add(le.clone());
        }
        return res;
    }
    
    public LocalDate getData(){
        return this.dataCarregamento;
    }
    
    public String getResolucao(){
        return this.resolucao;
    }
    
    public String getDuracao(){
        return this.duracao;
    }
    
    public List<String> getComentarios(){
        List<String> res = new ArrayList<>();
        for(String le : comentarios){
            res.add(le.clone());
        }
        return res;
    }
    
    public int getLikes(){
        return this.likes;
    }
    
    public int getDislikes(){
        return this.dislikes;
    }
    
    // sets
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setBytes(List<Byte> bytes1){
        this.bytes = new ArrayList<>();
        for(Byte le : bytes1){
            this.bytes.add(le.clone());
        }
    }
    
    public void setDate(LocalDate data){
        this.dataCarregamento = data;
    }
    
    public void setResolucao(String resolucao){
        this.resolucao = resolucao;
    }
    
    public void setDuracao(String duracao){
        this.duracao = duracao;
    }
    
    public void setComentarios(List<String> comentarios1){
        this.comentarios = new ArrayList<>();
        for(String le : comentarios1){
            this.comentarios.add(le.clone());
        }
    }
    
    public void setLikes(int likes){
        this.likes = likes;
    }
    
    public void setDislikes(int dislikes){
        this.dislikes = dislikes;
    }
    
    public videoYT clone(){
        return new videoYT(this);
    }
    
    
}
