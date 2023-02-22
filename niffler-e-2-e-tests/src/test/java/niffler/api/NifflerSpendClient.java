package niffler.api;

import niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NifflerSpendClient {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(NifflerSpendService.nifflerSpendUri)
            .client(createClient())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    private final NifflerSpendService nifflerSpendService = retrofit.create(NifflerSpendService.class);

    public OkHttpClient createClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();  // Перехватчик логов
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);    // Установка уровня логгирования
        return new OkHttpClient.Builder()    // Клиент для настройки retrofit
                .connectTimeout(60, TimeUnit.SECONDS)   // Время ожидания ответа от сервера
                .readTimeout(60, TimeUnit.SECONDS)  // Время ожидания чтения ответа от сервера
                .addInterceptor(logging)// Перехватчик запросов и ответов для построения отчетов
                .build();
    }

    public SpendJson createSpend(String username, SpendJson spend) throws Exception {
        return nifflerSpendService.addSpend(username, spend).execute().body();
    }

    public List<SpendJson> getSpends(String username) throws Exception {
        return nifflerSpendService.getSpends(username).execute().body();
    }

    public Response<Void> deleteSpends(String username, List<String> ids) throws Exception {
        return nifflerSpendService.deleteSpends(username, ids).execute();
    }
}
