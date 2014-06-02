package by.test.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Manager;
import ru.blogic.test.storage.Storage;
import ru.blogic.test.storage.Storage.Dictionary;
import ru.blogic.test.storage.Storage.Dictionary.Record;
import by.test.webservices.VocabloryRecord;

//@Service
public class RecordServiceStub implements RecordService {

	@Override
	public List<String> getAllVocabloryNames() {
		ArrayList<String> volabloryNames = new ArrayList<String>();
		volabloryNames.add("Dict11");
		volabloryNames.add("Dict12");
		return volabloryNames;
	}

	@Override
	public String getNumberOfRecords() {
		return new String("12345");
	}

	@Override
	public boolean addRecordToVoc(String aVocName, Long aContent) {
		return true;
	}

	@Override
	public List<VocabloryRecord> searchRecordsLessThan(Long aLessThan,
			String aVocName) throws Exception {
		Storage storage = Manager.getInstance().getStorage();
		String aaa = storage.getDictionariesNames().toString();
		Dictionary dictionary = storage.getDictionary(aVocName);
		List<String> recordIds = dictionary.getAllRecordsIds();
		ArrayList<VocabloryRecord> records = new ArrayList<VocabloryRecord>();

		Record record = dictionary.getRecord(recordIds.get(1));

		VocabloryRecord vocabloryRecord = new VocabloryRecord();
		vocabloryRecord.setContent(111);
		records.add(vocabloryRecord);
		vocabloryRecord = new VocabloryRecord();
		vocabloryRecord.setContent(222);
		records.add(vocabloryRecord);

		return records;
	}
}
