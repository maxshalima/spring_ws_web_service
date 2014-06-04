package by.test.services;

import java.util.List;

import by.test.webservices.VocabloryRecord;

public interface RecordService {

    public List<String> getAllVocabloryNames() throws Exception;

    public String getNumberOfRecords() throws Exception;

    public boolean addRecordToVoc(String aVocName, Long aContent);

    public List<VocabloryRecord> searchRecordsLessThan(String aVocName, Long aLessThan) throws Exception;
}
