package by.test.services.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import by.test.services.RecordService;
import by.test.webservices.VocabloryRecord;
import by.test.webservices.recordservice.AddRecordToVocabloryRequest;
import by.test.webservices.recordservice.AddRecordToVocabloryResponse;
import by.test.webservices.recordservice.GetAllVocabloriesRequest;
import by.test.webservices.recordservice.GetAllVocabloriesResponse;
import by.test.webservices.recordservice.GetNumberOfRecordsRequest;
import by.test.webservices.recordservice.GetNumberOfRecordsResponse;
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
				.searchRecordsLessThan(request.getContent(),
						request.getVocablory());
		response.getRecordList().addAll(vocabloryRecords);
		return response;
	}

	@PayloadRoot(localPart = "GetAllVocabloriesRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload
	GetAllVocabloriesResponse getAllVocablories(
			@RequestPayload GetAllVocabloriesRequest request) throws Exception {
		GetAllVocabloriesResponse response = new GetAllVocabloriesResponse();
		List<String> vocabloryList = recordService_i.getAllVocabloryNames();
		response.getVocabloriesList().addAll(vocabloryList);
		return response;
	}

	@PayloadRoot(localPart = "GetNumberOfRecordsRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload
	GetNumberOfRecordsResponse getNumberOfRecord(
			@RequestPayload GetNumberOfRecordsRequest request) throws Exception {
		GetNumberOfRecordsResponse response = new GetNumberOfRecordsResponse();
		String numberOfRecords = recordService_i.getNumberOfRecords();
		response.setRecordNumber(numberOfRecords);
		return response;
	}

	@PayloadRoot(localPart = "AddRecordToVocabloryRequest", namespace = TARGET_NAMESPACE)
	public @ResponsePayload
	AddRecordToVocabloryResponse addRecordToVoc(
			@RequestPayload AddRecordToVocabloryRequest request)
			throws Exception {
		AddRecordToVocabloryResponse response = new AddRecordToVocabloryResponse();
		boolean isSuccessed = recordService_i.addRecordToVoc(
				request.getVocabloryName(), request.getContent());
		response.setIsSuccessed(isSuccessed);
		return response;
	}

	public void setRecordService(RecordService recordService_p) {
		this.recordService_i = recordService_p;
	}
}
