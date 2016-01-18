import java.net.URL;

import java.net.HttpURLConnection;

import java.net.URLEncoder;

import java.io.InputStream;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.DataOutputStream;

import java.io.IOException;


public class WolframCloudCall {


public static String call(String str1, String str2) throws IOException {


URL _url = new URL("http://www.wolframcloud.com/objects/9bd54cb7-9253-47d7-bb55-928ce1b60a21");

        HttpURLConnection _conn = (HttpURLConnection) _url.openConnection();

        _conn.setRequestMethod("POST");

        _conn.setDoOutput(true);

        _conn.setDoInput(true);

        _conn.setUseCaches(false);

        _conn.setAllowUserInteraction(false);

        _conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        _conn.setRequestProperty("User-Agent", "EmbedCode-Java/1.0");

        DataOutputStream _out = new DataOutputStream(_conn.getOutputStream());


        _out.writeBytes("str1");

        _out.writeBytes("=");

        _out.writeBytes(URLEncoder.encode("" + str1, "UTF-8"));

        _out.writeBytes("&");

        _out.writeBytes("str2");

        _out.writeBytes("=");

        _out.writeBytes(URLEncoder.encode("" + str2, "UTF-8"));

        _out.writeBytes("&");


        _out.close();


        if (_conn.getResponseCode() != 200) {

            throw new IOException(_conn.getResponseMessage());

        }

        

        BufferedReader _rdr = new BufferedReader(new InputStreamReader(_conn.getInputStream()));

        StringBuilder _sb = new StringBuilder();

        String _line;

        while ((_line = _rdr.readLine()) != null) {

            _sb.append(_line);

        }

        _rdr.close();

        _conn.disconnect();

        return _sb.toString();

    }

}