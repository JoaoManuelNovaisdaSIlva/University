import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Mapa{

    final int altura = 20;

    final int largura = 20;
    private int[][] mapa;
    private int id_code;
    private ReadWriteLock lock;

    private int cod;

    public Mapa(){
        this.id_code = 0;
        int nTroti = 20;
        this.lock = new ReentrantReadWriteLock();
        mapa = new int[altura][largura];
        for (int i = 0; i < altura; i++){
            for (int a = 0; a < largura; a++){
                mapa[i][a] = 0;
            }
        }
        while (nTroti>0){
            Random random = new Random();
            int min = 0;
            int max = 19;
            int randomX = random.nextInt(max - min + 1) + min;
            int randomY = random.nextInt(max - min + 1) + min;
            mapa[randomX][randomY] += 1;
            nTroti--;
        }
    }

    public List<Localizacao> avaiable(Localizacao l){
        int xEsq = l.getX()-2;
        int xDir = l.getX()+2;
        int yCima = l.getY()-2;
        int yBaixo = l.getY()+2;
        if(xEsq < 0) xEsq = 0; if(xEsq > 20) xEsq = 20;
        if(xDir < 0) xDir = 0; if(xDir > 20) xEsq = 20;
        if(yCima < 0) yCima = 0; if(yCima > 20) yCima = 20;
        if(yBaixo < 0) yBaixo = 0; if(yBaixo > 20) yBaixo = 20;
        this.lock.readLock().lock();
        List <Localizacao> t = new ArrayList<Localizacao>();

        for(int i = yCima; i<yBaixo; i++){
            for(int a = xEsq; a<xDir; a++){
                if(mapa[i][a]>0){
                    t.add(new Localizacao(a,i));
                }
            }
        }
        this.lock.readLock().unlock();
        return  t;

    }

    public ReservasTrotinete aluga(Localizacao l){
        int minx=l.getX() - 2;if (minx < 0) minx = 0;
        int maxx=l.getX() + 2;if (maxx >= 20) maxx = 20-1;
        int miny=l.getY() - 2;if (miny < 0) miny = 0;
        int maxy=l.getY() + 2;if (maxy >= 20) maxy = 20-1;
        ReservasTrotinete reservasTrotinete = null;
        List<Localizacao> posList = new ArrayList<Localizacao>();
        this.lock.writeLock().lock();
        for (int i = miny; i <= maxy; i++){
            for (int j = minx; j <= maxx; j++){
                if (mapa[i][j] > 0 && Math.abs(i-l.getY()) + Math.abs(j-l.getX()) <= 2){
                    posList.add(new Localizacao(j, i));
                    break;
                }
            }
        }

        if (posList.size() == 0){
            this.lock.writeLock().unlock();
            return (new ReservasTrotinete(-1, new Localizacao(-1, -1)));
        }

        posList.sort((p1, p2) -> {
            int d1 = Math.abs(p1.getX() - l.getX()) + Math.abs(p1.getY() - l.getY());
            int d2 = Math.abs(p2.getX() - l.getX()) + Math.abs(p2.getY() - l.getY());
            return d1 - d2;
        });

        int i = posList.get(0).getX();
        int j = posList.get(0).getY();
        mapa[j][i] -= 1;
        int id=id_code++;
        this.lock.writeLock().unlock();

        reservasTrotinete = new ReservasTrotinete(id, new Localizacao(i, j));

        return reservasTrotinete;
    }

    public double arruma(ReservasTrotinete r){
        this.lock.writeLock().lock();
        mapa[r.getEnd().getY()][r.getEnd().getX()] += 1;
        this.lock.writeLock().unlock();
        int distancia = r.getDistance();
        double preco = distancia * 0.12;
        return preco;
    }

}