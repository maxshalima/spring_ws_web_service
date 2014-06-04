package by.test.services.vocabloryrecord;

import java.util.List;

import by.test.webservices.VocabloryRecord;

public interface VocabloryRecordService {

	public List<VocabloryRecord> getGetAllSortedVocabloryRecords(String aVocName)
			throws Exception;

}
