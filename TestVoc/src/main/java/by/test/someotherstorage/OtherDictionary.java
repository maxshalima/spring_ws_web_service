package by.test.someotherstorage;

import java.util.List;

import ru.blogic.test.storage.Storage.Dictionary;

public class OtherDictionary implements Dictionary {

	@Override
	public String addRecord(String arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllRecordsIds() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Record getRecord(String arg0) throws Exception {
		OtherRecord record = new OtherRecord();
		return record;
	}

	@Override
	public int getRecordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
