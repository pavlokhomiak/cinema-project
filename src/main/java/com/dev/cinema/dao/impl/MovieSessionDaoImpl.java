package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.MovieSessionDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger logger = Logger.getLogger(MovieSessionDaoImpl.class);

    @Override
    public MovieSession add(MovieSession session) {
        Session hibernateSession = null;
        Transaction transaction = null;
        try {
            hibernateSession = HibernateUtil.getSessionFactory().openSession();
            transaction = hibernateSession.beginTransaction();
            hibernateSession.persist(session);
            transaction.commit();
            logger.info("Movie session: " + session + " has added to DB");
            return session;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add MovieSession "
                    + session, e);
        } finally {
            if (hibernateSession != null) {
                hibernateSession.close();
            }
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("from MovieSession ms "
                    + "join fetch ms.movie m "
                    + "join fetch ms.cinemaHall "
                    + "where m.id = :movieId and ms.showTime "
                    + "between :start_of_day and :end_of_day ", MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("start_of_day", date.atStartOfDay());
            query.setParameter("end_of_day", date.atTime(LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get MovieSession list by movie id: "
                    + movieId + " and date: " + date, e);
        }
    }
}
