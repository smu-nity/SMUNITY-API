package com.smunity.graduation.domain.auth.service;

import com.smunity.graduation.domain.auth.dto.AuthCourseResponseDto;
import com.smunity.graduation.domain.auth.dto.AuthRequestDto;
import com.smunity.graduation.domain.auth.dto.AuthResponseDto;
import com.smunity.graduation.global.common.ErrorCode;
import com.smunity.graduation.global.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final String LOGIN_URL = "https://smsso.smu.ac.kr/Login.do";
    private final String BASE_URL = "https://smul.smu.ac.kr";

    public AuthResponseDto authenticate(AuthRequestDto requestDto) {
        JSONObject response = getData(requestDto, "/UsrSchMng/selectStdInfo.do");
        return AuthResponseDto.from(response.getJSONArray("dsStdInfoList"));
    }

    public List<AuthCourseResponseDto> getCourses(AuthRequestDto requestDto) {
        JSONObject response = getData(requestDto, "/UsrRecMatt/list.do");
        return AuthCourseResponseDto.from(response.getJSONArray("dsRecMattList"));
    }

    private Map<String, String> login(AuthRequestDto requestDto) {
        try {
            Connection.Response response = Jsoup.connect(LOGIN_URL)
                    .data("user_id", requestDto.username())
                    .data("user_password", requestDto.password())
                    .method(Connection.Method.POST)
                    .execute();
            if (response.url().toString().equals(LOGIN_URL))
                throw new CustomException(ErrorCode.AUTH_UNAUTHORIZED);
            return Jsoup.connect(BASE_URL.concat("/index.do"))
                    .method(Connection.Method.GET)
                    .cookies(response.cookies())
                    .execute()
                    .cookies();
        } catch (IOException e) {
            throw new CustomException(ErrorCode.AUTH_INTERNAL_SERVER_ERROR);
        }
    }

    private JSONObject getData(AuthRequestDto requestDto, String url) {
        Map<String, String> session = login(requestDto);
        try {
            URL apiUrl = new URL(BASE_URL.concat(url));
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            for (Map.Entry<String, String> cookie : session.entrySet())
                connection.addRequestProperty("Cookie", cookie.getKey() + "=" + cookie.getValue());
            String requestData = "@d#=@d1#&@d1#tp=dm&_AUTH_MENU_KEY=usrCPsnlInfoUpd-STD&@d1#strStdNo=".concat(requestDto.username());
            connection.setDoOutput(true);
            connection.getOutputStream().write(requestData.getBytes());

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);
            }
            return new JSONObject(response.toString());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.AUTH_INTERNAL_SERVER_ERROR);
        }
    }
}
