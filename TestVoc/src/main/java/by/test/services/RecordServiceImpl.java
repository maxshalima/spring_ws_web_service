package by.test.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Manager;
import ru.blogic.test.storage.Storage.Dictionary;
import ru.blogic.test.storage.Storage.Dictionary.Record;
import ru.blogic.test.storage.Storage.Dictionary.RecordAttr;
import by.test.webservices.VocabloryRecord;

@Service
public class RecordServiceImpl implements RecordService {

    @Override
    public List<String> getAllVocabloryNames() throws Exception {

        ((RecordService) AopContext.currentProxy()).searchRecordsLessThan("Dict1", new Long(21));

        return this.getDictionariesNames();
    }

    @Override
    @Cacheable("numberOfAllRecords")
    public String getNumberOfRecords() throws Exception {
        BigInteger count = BigInteger.valueOf(0);
        List<String> allVocNameList = this.getDictionariesNames();

        for (String vocName : allVocNameList) {
            int dictCount = this.getRecordCount(vocName);
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
    @Caching(evict = { @CacheEvict(value = "numberOfAllRecords", allEntries = true),
            @CacheEvict(value = "dictionary", key = "#aVocName"),
            @CacheEvict(value = "recordCount", key = "#aVocName"),
            @CacheEvict(value = "searchRecords", allEntries = true),
            @CacheEvict(value = "allSortedVocabloryRecords", key = "#aVocName") })
    public boolean addRecordToVoc(String aVocName, Long aContent) {

        try {
            Dictionary dictionary = this.getDictionary(aVocName);
            dictionary.addRecord(aContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Cacheable("searchRecords")
    public List<VocabloryRecord> searchRecordsLessThan(String aVocName, Long aLessThan) throws Exception {

        List<VocabloryRecord> allRecords = this.getGetAllSortedVocabloryRecords(aVocName);

        int i = 0;
        for (VocabloryRecord vocabloryRecord : allRecords) {
            if (vocabloryRecord.getContent() >= aLessThan)
                break;
            i++;
        }

        return allRecords.subList(0, i);
    }

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

    @Override
    //@Cacheable(value = "allRecordIdsFromVoc", key = "#aVocName")
    public List<String> getAllRecordsIds(String aVocName) throws Exception {
        return this.getDictionary(aVocName).getAllRecordsIds();
    }

    @Override
    @Cacheable("record")
    public Record getRecord(String aVocName, String anRecordId) throws Exception {
        return this.getDictionary(aVocName).getRecord(anRecordId);
    }

    @Override
    @Cacheable(value = "recordCount", key = "#aVocName")
    public int getRecordCount(String aVocName) throws Exception {
        return this.getDictionary(aVocName).getRecordCount();
    }

    @Override
    @Cacheable(value = "allSortedVocabloryRecords", key = "#aVocName")
    public List<VocabloryRecord> getGetAllSortedVocabloryRecords(String aVocName) throws Exception {
        List<String> allRecordsIds = this.getAllRecordsIds(aVocName);
        List<VocabloryRecord> allRecords = new ArrayList<VocabloryRecord>();

        for (String recId : allRecordsIds) {
            Record record = this.getRecord(aVocName, recId);
            VocabloryRecord vocabloryRecord = new VocabloryRecord();
            vocabloryRecord.setID(record.getId());
            vocabloryRecord.setContent(Long.parseLong((String) record.getAttributes().get(RecordAttr.CONTENT)));
            allRecords.add(vocabloryRecord);
        }

        Collections.sort(allRecords, new Comparator<VocabloryRecord>() {
            @Override
            public int compare(VocabloryRecord record1, VocabloryRecord record2) {
                return ((Long) record1.getContent()).compareTo((Long) record2.getContent());
            }
        });

        return allRecords;
    }
}
