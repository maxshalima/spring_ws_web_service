package by.test.services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Storage.Dictionary;
import by.test.services.dictionary.DictionaryService;
import by.test.services.record.RecordAccessService;
import by.test.services.vocabloryrecord.VocabloryRecordService;
import by.test.webservices.VocabloryRecord;

@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
	private RecordAccessService recordAccessService_i;

	@Autowired
	private DictionaryService dictionaryService_i;

	@Autowired
	private VocabloryRecordService vocabloryRecordService_i;

	@Override
	public List<String> getAllVocabloryNames() throws Exception {
		return dictionaryService_i.getDictionariesNames();
	}

	@Override
	@Cacheable("numberOfAllRecords")
	public String getNumberOfRecords() throws Exception {
		BigInteger count = BigInteger.valueOf(0);
		List<String> allVocNameList = dictionaryService_i
				.getDictionariesNames();

		for (String vocName : allVocNameList) {
			int dictCount = recordAccessService_i.getRecordCount(vocName);
			count = count.add(BigInteger.valueOf(dictCount));
		}
		return count.toString();
	}

	// @Caching(evict = { @CacheEvict(value = "numberOfAllRecords", allEntries =
	// true),
	// @CacheEvict(value = "dictionary", key = "#aVocName"),
	// @CacheEvict(value = "allRecordIdsFromVoc", key = "#aVocName"),
	// @CacheEvict(value = "recordCount", key = "#aVocName"),
	// @CacheEvict(value = "allSortedVocabloryRecords", key = "#aVocName"),
	// @CacheEvict(value = "searchRecords", allEntries = true) })

	@Override
	@Caching(evict = {
			@CacheEvict(value = "numberOfAllRecords", allEntries = true),
			@CacheEvict(value = "dictionary", key = "#aVocName"),
			@CacheEvict(value = "recordCount", key = "#aVocName"),
			@CacheEvict(value = "searchRecords", allEntries = true),
			@CacheEvict(value = "allSortedVocabloryRecords", key = "#aVocName") })
	public boolean addRecordToVoc(String aVocName, Long aContent) {

		try {
			Dictionary dictionary = dictionaryService_i.getDictionary(aVocName);
			dictionary.addRecord(aContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Cacheable("searchRecords")
	public List<VocabloryRecord> searchRecordsLessThan(String aVocName,
			Long aLessThan) throws Exception {

		List<VocabloryRecord> allRecords = vocabloryRecordService_i
				.getGetAllSortedVocabloryRecords(aVocName);

		int i = 0;
		for (VocabloryRecord vocabloryRecord : allRecords) {
			if (vocabloryRecord.getContent() >= aLessThan)
				break;
			i++;
		}

		return allRecords.subList(0, i);
	}
}
