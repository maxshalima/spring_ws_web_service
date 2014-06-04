package by.test.services.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Storage.Dictionary.Record;
import by.test.services.dictionary.DictionaryService;

@Service
public class RecordAccessServiceImpl implements RecordAccessService {

    @Autowired
    private DictionaryService dictionaryService_i;

    @Override
    //@Cacheable(value = "allRecordIdsFromVoc", key = "#aVocName")
    public List<String> getAllRecordsIds(String aVocName) throws Exception {
        return dictionaryService_i.getDictionary(aVocName).getAllRecordsIds();
    }

    @Override
    //@Cacheable("record")
    public Record getRecord(String aVocName, String anRecordId) throws Exception {
        return dictionaryService_i.getDictionary(aVocName).getRecord(anRecordId);
    }

    @Override
    @Cacheable(value = "recordCount", key = "#aVocName")
    public int getRecordCount(String aVocName) throws Exception {
        return dictionaryService_i.getDictionary(aVocName).getRecordCount();
    }

}
