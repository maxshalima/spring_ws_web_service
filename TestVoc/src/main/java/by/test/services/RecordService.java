package by.test.services;

import java.util.List;

import by.test.webservices.VocabloryRecord;

public interface RecordService {

	public List<String> getAllVocablories();

	public Long getNumberOfRecords();

	public boolean addRecordToVoc(String aVocName, Long aContent);

	public List<VocabloryRecord> searchRecordsLessThan(Long aLessThan)
			throws Exception;
}
