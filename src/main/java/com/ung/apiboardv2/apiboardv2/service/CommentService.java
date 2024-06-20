package com.ung.apiboardv2.apiboardv2.service;

import com.ung.apiboardv2.apiboardv2.dto.comment.CommentCreateRequest;
import com.ung.apiboardv2.apiboardv2.dto.comment.CommentDeleteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final JdbcTemplate jdbcTemplate;

    public boolean createComment(CommentCreateRequest req) {
        String sql = "CALL newComments(?, ?, ?)";
        Connection conn = null;
        CallableStatement cstmt = null;

        try{
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, req.getBoardId());
            cstmt.setInt(2, req.getMemberId());
            cstmt.setString(3, req.getComment());

            cstmt.execute();
            return true;
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
        return false;
    }

    public boolean deleteService(CommentDeleteRequest req) {
        String sql = "CALL deleteComment(?, ?, ?)";
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, req.getCommentId());
            cstmt.setInt(2, req.getBoardId());
            cstmt.setInt(3, req.getMemberId());

            cstmt.execute();
            return true;
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
        return false;
    }
}
