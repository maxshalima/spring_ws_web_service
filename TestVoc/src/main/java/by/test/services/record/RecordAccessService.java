package by.test.services.record;

import java.util.List;

import ru.blogic.test.storage.Storage.Dictionary.Record;

public interface RecordAccessService {

	public List<String> getAllRecordsIds(String aVocName) throws Exception;

	public Record getRecord(String aVocName, String anId) throws Exception;

	public int getRecordCount(String aVocName) throws Exception;
}
