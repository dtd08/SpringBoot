package hello.hellospring.controller;

public class MemberForm {
    private String name; // createMemberForm.html의 input 태그의 name 속성의 값과 이름이 같음 => 이 필드에 데이터 저장

    public String getName() {
        return name;
    }

    // Spring이 받은 데이터를 setter를 이용해 필드에 넣어줌
    public void setName(String name) {
        this.name = name;
    }
}
