package by.test.services.dictionary;

import java.util.List;

import ru.blogic.test.storage.Storage.Dictionary;

public interface DictionaryService {

	public List<String> getDictionariesNames() throws Exception;

	public Dictionary getDictionary(String aVocName) throws Exception;

}
