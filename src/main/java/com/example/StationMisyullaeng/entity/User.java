package com.example.StationMisyullaeng.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    //이거는 PK 설정을 위해 넣은 필드에요
    // 나중에 account_email 필드 권한이 필수 동의로 변경되면 이거 지우고 accountEmail 변수 사용하세요
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**kakao login api 응답 받은 데이터 목록 링크:
     *      https://developers.kakao.com/console/app/1254415/product/login/scope
     *응답 데이터 자료형 링크:
     *      https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
     */
    @Column(name = "profile_nickname")
    private String profileNickname; //프로필 닉네임

    @Column(name = "account_email")
    private String accountEmail;    //카카오계정(이메일)

    @Column(name = "name")
    private String name;            //이름

    @Column(name = "gender")
    private String gender;          //성별

    @Column(name = "age_range")
    private String ageRange;        //연령대

    @Column(name = "birthday")
    private String birthday;        //생일

    @Column(name = "birthyear")
    private String birthyear;       //출생 연도

    @Column(name = "phone_number")
    private String phoneNumber;     //카카오계정(전화번호)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();
}
