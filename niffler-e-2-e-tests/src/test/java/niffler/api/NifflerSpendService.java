package niffler.api;

import niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface NifflerSpendService {

    String nifflerSpendUri = "http://127.0.0.1:8093";

    @POST("/addSpend")
    Call<SpendJson> addSpend(@Query ("username") String username, @Body SpendJson spend);

    @GET("/spends")
    Call<List<SpendJson>> getSpends(@Query ("username") String username);

    @GET("/spends")
    Call<Void> deleteSpends(@Query ("username") String username, @Query ("ids") List<String> ids);
}
