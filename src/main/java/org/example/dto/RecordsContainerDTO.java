package org.example.dto;

import org.example.entity.Record;

import java.util.List;

public class RecordsContainerDTO
{
    private final List<Record> recordList;
    private final int numberOfDone;
    private final int numberOfActive;

    public RecordsContainerDTO(List<Record> recordList, int numberOfDone, int numberOfActive)
    {
        this.recordList = recordList;
        this.numberOfDone = numberOfDone;
        this.numberOfActive = numberOfActive;
    }

    public List<Record> getRecordList()
    {
        return recordList;
    }

    public int getNumberOfDone()
    {
        return numberOfDone;
    }

    public int getNumberOfActive()
    {
        return numberOfActive;
    }
}
