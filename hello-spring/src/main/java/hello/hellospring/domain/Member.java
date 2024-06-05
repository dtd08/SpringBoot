package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity // jpa가 관리하는 entity라는 표현
public class Member {

    @Id // pk 등록
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 ID 값을 자동으로 생성함(= Identity 전략)을 알림
    private Long id; // 데이터 구분을 위해 시스템이 정하는 값

//    @Column(name="username") // db의 컬럼명이 username으로 매핑됨  // 우리는 컬럼명과 변수명이 같으니 건들지 않아도 됨
    public String getName() {
        return name;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
