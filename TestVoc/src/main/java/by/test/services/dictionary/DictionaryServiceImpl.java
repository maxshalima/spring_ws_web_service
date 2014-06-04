package by.test.services.dictionary;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Manager;
import ru.blogic.test.storage.Storage.Dictionary;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Override
    @Cacheable("vocabloryNamesList")
    public List<String> getDictionariesNames() throws Exception {
        return Manager.getInstance().getStorage().getDictionariesNames();
    }

    @Override
    @Cacheable(value = "dictionary", key = "#aVocName")
    public Dictionary getDictionary(String aVocName) throws Exception {
        return Manager.getInstance().getStorage().getDictionary(aVocName);
    }

}
