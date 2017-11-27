package com.example.test42;

import android.util.Log;

import com.example.test42.data.model.Section;
import com.example.test42.data.remote.ApiUtils;
import com.example.test42.data.remote.SectionService;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SectionServiceUnitTest {
    List<Section> sections = null;

    @Test
    public void canLoadSections() throws Exception {
        sections = null;

        // even the way one does async tests haven't changed
        // https://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
        final CountDownLatch signal = new CountDownLatch(1);


        SectionService service = ApiUtils.getSectionService();
        service.getSections("nyhedscenter",
                            "6").enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                if(response.isSuccessful()) {
                    sections = response.body();

                } else {
                    Log.d("INFO", "" + response.code());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                Log.d("INFO", "Request could not be completed");
                signal.countDown();
            }
        });

        signal.await(30, TimeUnit.SECONDS);

        assertNotNull(sections);
        assertEquals(sections.size(), 19);
        assertEquals(sections.get(0).getTitle(), "Tophistorier");
        assertEquals(sections.get(0).getType(), Section.Type.Topstory);
        assertEquals(sections.get(18).getTitle(), "Regionale nyheder");
        assertEquals(sections.get(18).getType(), Section.Type.Regional);
    }
}