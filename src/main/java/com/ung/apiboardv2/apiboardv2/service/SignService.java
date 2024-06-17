package com.ung.apiboardv2.apiboardv2.service;

import com.ung.apiboardv2.apiboardv2.dto.sign.SignInRequest;
import com.ung.apiboardv2.apiboardv2.dto.sign.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Service
@RequiredArgsConstructor
public class SignService {
    private final JdbcTemplate jdbcTemplate;

    public SignInResponse signIn(SignInRequest req) {
        SignInResponse res = null;

        String sql = "CALL signIn(?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, req.getEmail());
            cstmt.setString(2, req.getPassword());

            // out 매개변수 등록
            cstmt.registerOutParameter(3, Types.INTEGER);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, Types.VARCHAR);
            cstmt.registerOutParameter(7, Types.VARCHAR);

            cstmt.execute();

            res = new SignInResponse(
                    cstmt.getInt(3),
                    cstmt.getString(4),
                    cstmt.getString(5),
                    cstmt.getString(6),
                    cstmt.getString(7)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
            }
        }
        return res;
    }

}

