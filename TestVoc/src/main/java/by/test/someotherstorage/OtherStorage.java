package by.test.someotherstorage;

import java.util.List;

import ru.blogic.test.storage.Storage;

public class OtherStorage implements Storage {

	@Override
	public List<String> getDictionariesNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dictionary getDictionary(String arg0) {
		OtherDictionary fakeDictionary = new OtherDictionary();
		return fakeDictionary;
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

}
