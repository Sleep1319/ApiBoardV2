package com.ung.apiboardv2.apiboardv2.service;

import com.ung.apiboardv2.apiboardv2.domain.board.Board;
import com.ung.apiboardv2.apiboardv2.domain.board.Comment;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardCreateRequest;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardDeleteRequest;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardGetAllResponse;
import com.ung.apiboardv2.apiboardv2.dto.board.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final JdbcTemplate jdbcTemplate;

    public boolean createBoard(BoardCreateRequest req) {
        String sql = "CALL createBoard(?, ?, ?)";
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, req.getMemberId());
            cstmt.setString(2, req.getTitle());
            cstmt.setString(3, req.getContent());

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

    public List<BoardGetAllResponse> getAllBoard() {
        List<BoardGetAllResponse> boardList = new ArrayList<>();

        String sql = "CALL getAllBoard()";
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);

            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                BoardGetAllResponse res = new BoardGetAllResponse(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3));
                boardList.add(res);
            }

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
        return boardList;
    }

    public Board getBoardById(int boardId) {
        String sql1 = "CALL getByBoardId(?, ?, ?, ?, ?, ?)";
        String sql2 = "CALL getByBoardIdComment(?)";
        Connection conn = null;
        CallableStatement boardCstmt = null;
        CallableStatement commentCstmt = null;

        try{
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            boardCstmt = conn.prepareCall(sql1);
            commentCstmt = conn.prepareCall(sql2);

            boardCstmt.setInt(1, boardId);

            boardCstmt.registerOutParameter(2, Types.INTEGER);
            boardCstmt.registerOutParameter(3, Types.VARCHAR);
            boardCstmt.registerOutParameter(4, Types.VARCHAR);
            boardCstmt.registerOutParameter(5, Types.INTEGER);
            boardCstmt.registerOutParameter(6, Types.VARCHAR);

            boardCstmt.execute();

            commentCstmt.setInt(1, boardId);
            ResultSet rs = commentCstmt.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                comments.add(comment);
            }

            Board board = new Board(
                    boardCstmt.getInt(2),
                    boardCstmt.getString(3),
                    boardCstmt.getString(4),
                    boardCstmt.getInt(5),
                    boardCstmt.getString(6),
                    comments
            );
            return board;
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            if (boardCstmt != null) {
                try {
                    boardCstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (commentCstmt != null) {
                try {
                    commentCstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
            }
        }
        return null;
    }

    public boolean updateBoard(BoardUpdateRequest req) {
        String sql = "CALL updateBoard(?, ?, ?)";
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, req.getBoardId());
            cstmt.setInt(2,req.getMemberId());
            cstmt.setString(3, req.getContent());

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

    public boolean deleteBoard(BoardDeleteRequest req) {
        String sql = "CALL deleteBoard(?, ?)";
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, req.getBoardId());
            cstmt.setInt(2, req.getMemberId());

            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
            }
        }
        return false;
    }
}
