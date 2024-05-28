package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 매우 고전적인 순수 jdbc 스타일 (약 20여년 전)
// 메모리로 구현한 MemoryMemberRepository와는 달리 jdbc로 구현함
public class JdbcMemberRepository implements MemberRepository {
    
    private final DataSource dataSource; // 접속정보 데이터 소스

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; // 저장 쿼리문  // '?'는 전달될 값(파라미터 바인딩 될 값)

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // 데이터베이스 커넥션 받기

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 문장 작성  // 'RETURN_GENERATED_KEYS'는 db의 다음 id값을 가져오기 위해 쓰임
            pstmt.setString(1, member.getName()); // 파라미터 바인딩

            pstmt.executeUpdate(); // db에 쿼리 전송
            rs = pstmt.getGeneratedKeys(); // 'RETURN_GENERATED_KEYS'에서 얻은 키에 맞는 레코드 반환

            if (rs.next()) { // 결과 값이 있다면
                member.setId(rs.getLong(1)); // 받은 결과 세팅
            } else {
                throw new SQLException("id 조회 실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 데이터베이스 커넥션 종료  // 외부 네트워크와 연결되었기 때문에 사용 후 리소스 반환을 해줘야 함
        }

        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?"; // 조회 쿼리

        // 연결 객체
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // 커넥션 받기
            pstmt = conn.prepareStatement(sql); // 전송할 쿼리문 작성
            pstmt.setLong(1, id); // 파라미터 바인딩

            rs = pstmt.executeQuery(); // 조회는 excuteQuery

            if(rs.next()) { // 받은 데이터가 있으면 결과 반환
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member); 
            } else {
                return Optional.empty(); // 조회 결과 없음 반환
            }
        } catch(Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * form member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while(rs.next()) { // 더 이상 가져온 결과가 없을때까지 반복
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member); // id, name 세팅 후 members에 추가
            }

            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource); // 모든 메소드마다 새로 커넥션을 받으면 모두 새로운 연결이 됨 ('DataSourceUtils'는 그런 현상을 방지해줌) // 참고만
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn,dataSource); // 커넥션을 얻을 때와 마찬가지로 연결 해제도 'DataSourceUtils'를 통해 해야함
    }
}
