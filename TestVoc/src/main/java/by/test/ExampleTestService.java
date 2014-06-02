package by.test;

import java.util.List;

import ru.blogic.test.storage.Manager;
import ru.blogic.test.storage.Storage;
import ru.blogic.test.storage.Storage.Dictionary;
import ru.blogic.test.storage.Storage.Dictionary.Record;

public class ExampleTestService {

	public List<String> getAllVocablories() throws Exception {
		Storage storage = Manager.getInstance().getStorage();
		return storage.getDictionariesNames();
	}

	public Long getNumberOfRecords() throws Exception {
		Storage storage = Manager.getInstance().getStorage();
		return new Long(00);
	}

	public void addRecordToVoc(String aVocName, Long aContent) throws Exception {
		Storage storage = Manager.getInstance().getStorage();
		Dictionary Dictionary = storage.getDictionary(aVocName);
		Dictionary.addRecord(aContent.toString());
	}

	public List<Record> searchRecsLessThan(Long aLessThan, String aVocName)
			throws Exception {
		Storage storage = Manager.getInstance().getStorage();
		Dictionary Dictionary = storage.getDictionary(aVocName);
		Record record = Dictionary.getRecord("sssss");
		return null;
	}

}
