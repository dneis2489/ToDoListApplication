package org.example.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class RecordDao
{
    private final EntityManagerFactory entityManagerFactory;

    public RecordDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public  List<Record> getAllRecords()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try
        {
            entityManager.getTransaction().begin();

            Query query = entityManager.createQuery("SELECT r from Record r");
            List<Record> records = query.getResultList();

            entityManager.getTransaction().commit();
            return records;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return Collections.emptyList();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void saveRecord(Record record)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(record);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try
        {
            entityManager.getTransaction().begin();

            Record record = entityManager.find(Record.class, id);
            record.setStatus(newStatus);
            entityManager.merge(record);

            Query query = entityManager.createQuery("UPDATE Record SET status = :newStatus where id = :id");
            query.setParameter("newStatus", newStatus);
            query.setParameter("id", id);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        finally
        {
            entityManager.close();
        }
    }

    public void deleteRecord(int id)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try
        {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE FROM Record where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        finally
        {
            entityManager.close();
        }
    }
}
