package org.example.service;

import org.example.dao.RecordDao;
import org.example.dto.RecordsContainerDTO;
import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class RecordService
{
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao)
    {
        this.recordDao = recordDao;
    }

    @Transactional(readOnly = true)
    public RecordsContainerDTO getAllRecords(String filterMode)
    {
        List<Record> records =  recordDao.getAllRecords();
        int numberOfDone = (int) records.stream()
                .filter((record)  -> record.getStatus() == RecordStatus.DONE)
                .count();

        int numberOfActive = (int) records.stream()
                .filter((record)  -> record.getStatus() == RecordStatus.ACTIVE)
                .count();

        if(filterMode == null || filterMode.isBlank())
        {
            return new RecordsContainerDTO(records, numberOfDone, numberOfActive);
        }

        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .toList();
        String filterModeToUpperCase = filterMode.toUpperCase();
        if(allowedFilterModes.contains(filterModeToUpperCase))
        {
            List<Record> filteredRecords = records.stream()
                    .filter((record) -> record.getStatus() == RecordStatus.valueOf(filterModeToUpperCase))
                    .toList();
            return new RecordsContainerDTO(filteredRecords, numberOfDone, numberOfActive);
        }
        else
        {
            return new RecordsContainerDTO(records, numberOfDone, numberOfActive);
        }
    }

    public void saveRecord(String title)
    {
        if(title != null && !title.isBlank())
        {
            recordDao.saveRecord(new Record(title));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus)
    {
        recordDao.updateRecordStatus(id, newStatus);
    }

    public void deleteRecord(int id)
    {
        recordDao.deleteRecord(id);
    }
}
