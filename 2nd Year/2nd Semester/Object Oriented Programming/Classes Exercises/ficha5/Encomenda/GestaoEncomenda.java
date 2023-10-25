
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Write a description of class GestaoEncomenda here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GestaoEncomenda
{
    private Set<Encomenda> setEncomenda;
    
    public GestaoEncomenda(){
        this.setEncomenda = new HashSet<>();
    }
    
    public GestaoEncomenda(List <Encomenda> linhas){
        this.setEncomenda = new HashSet<>();
        
        for(Encomenda a: linhas)
            setEncomenda.add(a);
    }
}
