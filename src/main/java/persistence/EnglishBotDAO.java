package persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EnglishBotDAO {

    private SessionFactory sessionFactory;

    public EnglishBotDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static SessionFactory buildSessionFactory(){
        return  new Configuration()
                .configure()
                .addAnnotatedClass(UserWords.class)
                .buildSessionFactory();
    }
}
