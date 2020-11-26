package space.harbour.java;

import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask<String, Integer, List<Student>> {
    private URL url;
    private RecyclerView v;

    public MyAsyncTask(String s, RecyclerView view) throws MalformedURLException {
        url = new URL(s);
        v = view;
    }

    @Override
    protected List<Student> doInBackground(String[] objects) {
        List<Student> students = new ArrayList<>();
        try {
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            StringBuilder response = new StringBuilder();
            try {
                InputStreamReader in = new InputStreamReader(c.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(in);
                String inputLine;
                while ((inputLine = br.readLine()) != null)
                    response.append(inputLine);
            } finally {
                c.disconnect();
            }
            JSONArray array = new JSONArray(response.toString());

            int n = array.length();
            for (int i = 0; i < n; i++) {
                students.add(Student.fromJsonObject(array.getJSONObject(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    protected void onPostExecute(List<Student> students) {
        super.onPostExecute(students);

        MyAdapter adapter = new MyAdapter(students);
        v.setAdapter(adapter);
    }
}
