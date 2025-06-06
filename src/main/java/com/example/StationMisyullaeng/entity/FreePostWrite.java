// src/main/java/com/example/StationMisyullaeng/entity/PostWrite.java

package com.example.StationMisyullaeng.entity;

// import com.fasterxml.jackson.annotation.JsonManagedReference; // 이 임포트도 없어야 합니다.
import jakarta.persistence.*;
import lombok.*;
// import com.example.StationMisyullaeng.entity.Comment; // 이 임포트도 없어야 합니다.
// import java.util.ArrayList; // 이 임포트도 없어야 합니다.
// import java.util.List; // 이 임포트도 없어야 합니다.

import java.time.LocalDateTime; // 이 임포트는 createdAt 때문에 필요합니다.

@Entity
@Table(name = "board_free")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FreePostWrite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // ❗️❗️❗️ 여기가 가장 중요합니다. 아래 코드가 완전히 제거되었는지 확인하세요. ❗️❗️❗️
    // @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonManagedReference
    // private List<Comment> comments = new ArrayList<>();
}