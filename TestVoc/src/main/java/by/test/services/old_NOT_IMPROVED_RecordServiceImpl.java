package by.test.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Manager;
import ru.blogic.test.storage.Storage;
import ru.blogic.test.storage.Storage.Dictionary;
import ru.blogic.test.storage.Storage.Dictionary.Record;
import ru.blogic.test.storage.Storage.Dictionary.RecordAttr;
import by.test.webservices.VocabloryRecord;

//@Service
public class old_NOT_IMPROVED_RecordServiceImpl implements RecordService {

	@Override
	@Cacheable("vocabloryList")
	public List<String> getAllVocabloryNames() throws Exception {
		// searchRecordsLessThan((long)1,"Dict1");
		Storage storage = Manager.getInstance().getStorage();
		return storage.getDictionariesNames();
	}

	@Override
	@Cacheable("recordNumber")
	public String getNumberOfRecords() throws Exception {
		Storage storage = Manager.getInstance().getStorage();

		BigInteger count = BigInteger.valueOf(0);
		List<String> allVocNameList = getAllVocabloryNames();
		for (String vocName : allVocNameList) {
			Dictionary dictionary = storage.getDictionary(vocName);
			int dictCount = dictionary.getRecordCount();
			count = count.add(BigInteger.valueOf(dictCount));
		}

		return count.toString();
	}

	@Override
	@CacheEvict(value = { "searchRecords", "recordNumber" }, allEntries = true)
	public boolean addRecordToVoc(String aVocName, Long aContent) {
		Storage storage;
		try {
			storage = Manager.getInstance().getStorage();
			Dictionary dictionary = storage.getDictionary(aVocName);
			dictionary.addRecord(aContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Cacheable("searchRecords")
	public List<VocabloryRecord> searchRecordsLessThan(Long aLessThan,
			String aVocName) throws Exception {
		Storage storage = Manager.getInstance().getStorage();
		Dictionary dictionary = storage.getDictionary(aVocName);
		List<String> allRecordsIds = dictionary.getAllRecordsIds();
		List<VocabloryRecord> allRecords = new ArrayList<VocabloryRecord>();

		for (String recId : allRecordsIds) {
			Record record = dictionary.getRecord(recId);
			VocabloryRecord vocabloryRecord = new VocabloryRecord();
			vocabloryRecord.setID(record.getId());
			vocabloryRecord.setContent(Long.parseLong((String) record
					.getAttributes().get(RecordAttr.CONTENT)));
			allRecords.add(vocabloryRecord);
		}

		Collections.sort(allRecords, new Comparator<VocabloryRecord>() {
			@Override
			public int compare(VocabloryRecord record1, VocabloryRecord record2) {
				Long conten1 = record1.getContent();
				Long conten2 = record2.getContent();
				return conten1.compareTo(conten2);
			}
		});

		int i;
		for (i = 0; i < allRecords.size(); i++) {
			VocabloryRecord vocabloryRecord = allRecords.get(i);
			Long recContent = vocabloryRecord.getContent();
			if (recContent > aLessThan)
				break;
		}

		return allRecords.subList(0, i);
	}
}
