package pl.edu.wat.spz.activities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import pl.edu.wat.spz.entity.Page;
import pl.edu.wat.spz.entity.RecipeHistory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJson() {
        String JSON = "{\"content\":[{\"id\":1,\"medicalVisit\":{\"id\":102,\"patient\":{\"id\":100,\"firstName\":\"AAAA\",\"lastName\":\"AAAAAAA\",\"email\":\"mamm@ds.pl\",\"enabled\":true,\"acceptTerms\":true,\"nationalId\":\"grg\",\"address\":\"tr\",\"postalCode\":\"tr\",\"city\":\"tr\",\"mobileNumber\":\"r\",\"idCardNo\":\"rt\"},\"doctorSpecialization\":{\"id\":1,\"doctor\":{\"id\":1,\"firstName\":\"Jan\",\"lastName\":\"Kowalski\",\"email\":\"doctor@op.pl\",\"enabled\":true,\"acceptTerms\":true,\"nationalId\":\"96440525555\",\"address\":\"q\",\"postalCode\":\"55-555\",\"city\":\"q\",\"mobileNumber\":\"880656541\",\"idCardNo\":\"8787878787878\",\"title\":\"dr.\",\"price\":300.00,\"specializations\":[{\"id\":1,\"name\":\"laryngolog\"}]},\"specialization\":{\"id\":1,\"name\":\"laryngolog\"}},\"doctorTimetable\":{\"id\":1,\"date\":\"2019-04-19 15:00\",\"enable\":false},\"cancel\":false},\"receiptDate\":\"2019-04-20 18:51\",\"medicineName\":\"Diesgomax\",\"comment\":\"2x dziennie, rano i wieczorem\"}],\"pageable\":{\"sort\":{\"sorted\":true,\"unsorted\":false},\"pageSize\":3,\"pageNumber\":0,\"offset\":0,\"paged\":true,\"unpaged\":false},\"last\":true,\"totalElements\":1,\"totalPages\":1,\"first\":true,\"sort\":{\"sorted\":true,\"unsorted\":false},\"numberOfElements\":1,\"size\":3,\"number\":0}";

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Page<RecipeHistory> page = gson.fromJson(JSON, new TypeToken<Page<RecipeHistory>>() {
        }.getType());
        assertNotNull(page);
    }
}