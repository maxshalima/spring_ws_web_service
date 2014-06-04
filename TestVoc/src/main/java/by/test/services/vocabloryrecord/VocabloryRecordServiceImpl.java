package by.test.services.vocabloryrecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.blogic.test.storage.Storage.Dictionary.Record;
import ru.blogic.test.storage.Storage.Dictionary.RecordAttr;
import by.test.services.dictionary.DictionaryService;
import by.test.services.record.RecordAccessService;
import by.test.webservices.VocabloryRecord;

@Service
public class VocabloryRecordServiceImpl implements VocabloryRecordService {

    @Autowired
    private RecordAccessService recordAccessService_i;

    @Autowired
    private DictionaryService dictionaryService_i;

    @Override
    @Cacheable(value = "allSortedVocabloryRecords", key = "#aVocName")
    public List<VocabloryRecord> getGetAllSortedVocabloryRecords(String aVocName) throws Exception {
        List<String> allRecordsIds = recordAccessService_i.getAllRecordsIds(aVocName);
        List<VocabloryRecord> allRecords = new ArrayList<VocabloryRecord>();

        for (String recId : allRecordsIds) {
            Record record = recordAccessService_i.getRecord(aVocName, recId);
            VocabloryRecord vocabloryRecord = new VocabloryRecord();
            vocabloryRecord.setID(record.getId());
            vocabloryRecord.setContent(Long.parseLong((String) record.getAttributes().get(RecordAttr.CONTENT)));
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

        return allRecords;
    }

}
