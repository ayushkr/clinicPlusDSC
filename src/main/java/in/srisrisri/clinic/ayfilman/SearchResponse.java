package in.srisrisri.clinic.ayfilman;


import in.srisrisri.clinic.responses.JsonResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse extends JsonResponse {

private List<ResultOfSearch> resultOfSearchList=new ArrayList<>();

    public List<ResultOfSearch> getResultOfSearchList() {
        return resultOfSearchList;
    }

    public void setResultOfSearchList(List<ResultOfSearch> resultOfSearchList) {
        this.resultOfSearchList = resultOfSearchList;
    }
}
