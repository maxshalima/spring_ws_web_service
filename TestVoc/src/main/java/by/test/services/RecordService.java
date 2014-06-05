package by.test.services;

import java.util.List;

import ru.blogic.test.storage.Storage.Dictionary;
import ru.blogic.test.storage.Storage.Dictionary.Record;

import by.test.webservices.VocabloryRecord;

public interface RecordService {

    public List<String> getAllVocabloryNames() throws Exception;

    public String getNumberOfRecords() throws Exception;

    public boolean addRecordToVoc(String aVocName, Long aContent);

    public List<VocabloryRecord> searchRecordsLessThan(String aVocName, Long aLessThan) throws Exception;

    public List<String> getDictionariesNames() throws Exception;

    public Dictionary getDictionary(String aVocName) throws Exception;

    public List<String> getAllRecordsIds(String aVocName) throws Exception;

    public Record getRecord(String aVocName, String anId) throws Exception;

    public int getRecordCount(String aVocName) throws Exception;

    public List<VocabloryRecord> getGetAllSortedVocabloryRecords(String aVocName) throws Exception;
}
