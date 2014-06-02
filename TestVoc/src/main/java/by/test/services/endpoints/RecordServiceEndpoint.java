package by.test.services.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import by.test.services.RecordService;
import by.test.webservices.VocabloryRecord;
import by.test.webservices.recordservice.SearchRecordsRequest;
import by.test.webservices.recordservice.SearchRecordsResponse;

@Endpoint
public class RecordServiceEndpoint {
	private static final String TARGET_NAMESPACE = "http://by/test/webservices/recordservice";
	@Autowired
	private RecordService recordService_i;

	@PayloadRoot(localPart = "SearchRecordsRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload
	SearchRecordsResponse searchRecordsLessThan(
			@RequestPayload SearchRecordsRequest request) throws Exception {
		SearchRecordsResponse response = new SearchRecordsResponse();
		List<VocabloryRecord> vocabloryRecords = recordService_i
				.searchRecordsLessThan(request.getContent());
		response.getRecordList().addAll(vocabloryRecords);
		return response;
	}

	public void setRecordService(RecordService recordService_p) {
		this.recordService_i = recordService_p;
	}
}
