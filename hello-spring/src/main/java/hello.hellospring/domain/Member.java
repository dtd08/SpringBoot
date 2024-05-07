package hello.hellospring.domain;

public class Member {
    private Long id; // 데이터 구분을 위해 시스템이 정하는 값
    private String name; // 회원 이름


    public String getName() {
        return name;
    }

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
