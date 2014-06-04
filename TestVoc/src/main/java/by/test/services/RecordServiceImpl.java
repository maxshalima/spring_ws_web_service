package by.test.services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Manager;
import ru.blogic.test.storage.Storage;
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
    private DictionaryService DictionaryService_i;

    @Autowired
    private VocabloryRecordService vocabloryRecordService_i;

    @Override
    public List<String> getAllVocabloryNames() throws Exception {
        return DictionaryService_i.getDictionariesNames();
    }

    @Override
    @Cacheable("numberOfAllRecords")
    public String getNumberOfRecords() throws Exception {
        BigInteger count = BigInteger.valueOf(0);
        List<String> allVocNameList = DictionaryService_i.getDictionariesNames();

        for (String vocName : allVocNameList) {
            int dictCount = recordAccessService_i.getRecordCount(vocName);
            count = count.add(BigInteger.valueOf(dictCount));
        }
        return count.toString();
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "numberOfAllRecords", allEntries = true),
            @CacheEvict(value = "dictionary", key = "#aVocName"),
            @CacheEvict(value = "allRecordIdsFromVoc", key = "#aVocName"),
            @CacheEvict(value = "recordCount", key = "#aVocName"),
            @CacheEvict(value = "allSortedVocabloryRecords", key = "#aVocName"),
            @CacheEvict(value = "searchRecords", allEntries = true) })
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
    public List<VocabloryRecord> searchRecordsLessThan(Long aLessThan, String aVocName) throws Exception {

        List<VocabloryRecord> allRecords = vocabloryRecordService_i.getGetAllSortedVocabloryRecords(aVocName);

        int i;
        for (i = 0; i < allRecords.size(); i++) {
            VocabloryRecord vocabloryRecord = allRecords.get(i);
            Long recContent = vocabloryRecord.getContent();
            if (recContent >= aLessThan)
                break;
        }

        return allRecords.subList(0, i);
    }
}
