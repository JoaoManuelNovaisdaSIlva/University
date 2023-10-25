import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Stack here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Stack
{
    private List<String> stack1;
    
    public Stack(){
        this.stack1 = new ArrayList();
    }
    
    public String top(){
        return this.stack1.get(this.stack1.size()-1);
    }
    
    public void push(String s){
        this.stack1.add(s);
    }
    
    public void pop(){
        if(this.stack1.isEmpty() == false){
            this.stack1.remove(this.stack1.size()-1);
        }
    }
    
    public boolean empty(){
        return false;
    }
}
