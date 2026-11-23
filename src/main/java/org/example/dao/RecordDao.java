package org.example.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.example.entity.Record;
import org.example.entity.RecordStatus;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecordDao
{
    @PersistenceContext
    private EntityManager entityManager;


    public  List<Record> getAllRecords()
    {
        Query query = entityManager.createQuery("SELECT r from Record r order by r.id asc");
        List<Record> records = query.getResultList();
        return records;
    }

    public void saveRecord(Record record)
    {
        entityManager.persist(record);
    }

    public void updateRecordStatus(int id, RecordStatus newStatus)
    {
        Query query = entityManager.createQuery("UPDATE Record SET status = :newStatus where id = :id");
        query.setParameter("newStatus", newStatus);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void deleteRecord(int id)
    {
        Query query = entityManager.createQuery("DELETE FROM Record where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
