package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.RoleDao;
import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.model.Role;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private static final Logger logger = Logger.getLogger(RoleDaoImpl.class);

    private SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role add(Role role) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            logger.info("Role: " + role + " has added to DB");
            return role;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add Role " + role, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Role> getRoleByName(Role.RoleName roleName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Role> query = session.createQuery("from Role r "
                    + "where r.roleName = :roleName", Role.class);
            query.setParameter("roleName", roleName);
            return query.uniqueResultOptional();
        }
    }
}
