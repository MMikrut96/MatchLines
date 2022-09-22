package pl.edu.wat.kulki.pz.utils.database;

import javafx.concurrent.Task;
import pl.edu.wat.kulki.pz.Main;
import pl.edu.wat.kulki.pz.exceptions.LoadFromDatabaseException;
import pl.edu.wat.kulki.pz.utils.ResourceLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.ExecutorService;

public class GameResult {

    private static ExecutorService executorService = Main.getExecutorService();

    public GameResult(){
    }

    public void saveResult(String playerName, String points){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(ResourceLoader.property("database.name"));
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                Score score = new Score();
                score.playerName = playerName;
                score.score = points;
                em.persist(score);
                em.getTransaction().commit();
                em.close();
                emf.close();
                return null;
            }
        };
        executorService.submit(task);
    }

    public void getScoreList(SimpleHandler<ResultList> handler){
        Task<ResultList> task = new Task<ResultList>() {
            @Override
            protected ResultList call() throws Exception {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory(ResourceLoader.property("database.name"));
                EntityManager em = emf.createEntityManager();
                ResultList list =  new ResultList(
                        em.createQuery("select s.playerName from Score s order by s.score desc ").setMaxResults(15).getResultList(),
                        em.createQuery("select s.score from Score s order by s.score desc ").setMaxResults(15).getResultList());
                em.close();
                emf.close();
                return list;
            }
        };
        task.setOnFailed(event ->{
            throw new LoadFromDatabaseException();
        });
        task.setOnSucceeded(event ->{
            handler.handle(task.getValue());
        });
        executorService.submit(task);
    }

}
