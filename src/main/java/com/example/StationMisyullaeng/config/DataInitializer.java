package com.example.StationMisyullaeng.config;

import com.example.StationMisyullaeng.entity.KakaoUser;
import com.example.StationMisyullaeng.entity.Restaurant;
import com.example.StationMisyullaeng.entity.Review;
import com.example.StationMisyullaeng.entity.Store;
import com.example.StationMisyullaeng.entity.UserGrade;
import com.example.StationMisyullaeng.repository.KakaoUserRepository;
import com.example.StationMisyullaeng.repository.RestaurantRepository;
import com.example.StationMisyullaeng.repository.ReviewRepository;
import com.example.StationMisyullaeng.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final KakaoUserRepository kakaoUserRepository;
    private final StoreRepository storeRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- 데이터 초기화 시작 ---");

        // 1. KakaoUser 생성
        KakaoUser user1 = KakaoUser.builder()
                .kakaoId("1234567890") // 실제 카카오 ID (문자열)
                .nickname("테스터1_일반유저")
                .email("test1@example.com")
                .profileImage("https://via.placeholder.com/40/FF5733/FFFFFF?Text=T1")
                .grade(UserGrade.GENERAL)
                .build();
        user1 = kakaoUserRepository.save(user1);
        System.out.println("생성된 사용자: " + user1.getNickname() + " (ID: " + user1.getId() + ")");


        KakaoUser user2 = KakaoUser.builder()
                .kakaoId("4280445397") // 프론트엔드에서 사용한 점주 kakaoId
                .nickname("테스터2_점주")
                .email("test2@example.com")
                .profileImage("https://via.placeholder.com/40/33FF57/FFFFFF?Text=T2")
                .grade(UserGrade.OWNER)
                .build();
        user2 = kakaoUserRepository.save(user2);
        System.out.println("생성된 점주: " + user2.getNickname() + " (ID: " + user2.getId() + ")");


        // 2. Store 생성 (점주 user2에 연결)
        Store store1 = Store.builder()
                .name("맛있는밥상")
                .address("서울특별시 송파구")
                .hours("매일 11:00 - 22:00")
                .phone("02-1234-5678")
                .description("점주가 직접 운영하는 건강한 한식 맛집입니다.")
                .kakaoId(user2.getKakaoId())
                .contact("010-1111-2222")
                .registrationNumber("123-45-67890")
                .category("한식")
                .imageUrl("https://via.placeholder.com/600x400/FFDDC1/000000?Text=K-Food")
                .meetingStation("잠실") // ★★★ '잠실역' -> '잠실' 로 수정 ★★★
                .build();
        store1 = storeRepository.save(store1);
        System.out.println("생성된 가게: " + store1.getName() + " (ID: " + store1.getStoreId() + ")");


        // 3. Restaurant 생성 (store1에 연결)
        // Restaurant의 stationName은 Store의 meetingStation과 일치해야 합니다. (이제 '역' 없음)
        Restaurant restaurant1 = Restaurant.builder()
                .name("맛있는밥상 본점")
                .stationName(store1.getMeetingStation()) // ★★★ store1의 meetingStation ('잠실') 사용 ★★★
                .category("한식")
                .rating(4.5)
                .reviewCount(1)
                .imageUrl("https://via.placeholder.com/400x300/A2D2FF/FFFFFF?Text=Restaurant1")
                .description("잠실역 근처 최고의 한식 맛집!")
                .store(store1)
                .build();
        restaurant1 = restaurantRepository.save(restaurant1);
        System.out.println("생성된 레스토랑: " + restaurant1.getName() + " (ID: " + restaurant1.getId() + ")");


        // 4. Review 생성 (restaurant1에 연결)
        Review review1 = Review.builder()
                .restaurant(restaurant1)
                .user(user1)
                .author("김미슐랭")
                .title("인생 맛집 등극!")
                .content("정말 든든한 한 끼였습니다. 반찬도 깔끔하고 서비스도 최고였어요. 강추합니다!")
                .rate(5)
                .imagePath("https://via.placeholder.com/200x150/FFECB3/000000?Text=ReviewPic")
                .createdAt(LocalDateTime.now().minusDays(5))
                .build();
        review1 = reviewRepository.save(review1);
        System.out.println("생성된 리뷰: " + review1.getTitle() + " (ID: " + review1.getReviewId() + ")");

        // 5. 점주 답글이 달린 Review 생성 (restaurant1에 연결)
        Review review2 = Review.builder()
                .restaurant(restaurant1)
                .user(user1)
                .author("박평가")
                .title("깔끔하고 좋네요!")
                .content("점심으로 먹었는데 깔끔하고 좋았습니다. 양도 푸짐해요.")
                .rate(4)
                .imagePath(null)
                .createdAt(LocalDateTime.now().minusDays(2))
                .ownerReplyContent("소중한 리뷰 감사합니다! 다음에 또 찾아주세요 :)")
                .ownerRepliedAt(LocalDateTime.now().minusDays(1))
                .build();
        review2 = reviewRepository.save(review2);
        System.out.println("생성된 리뷰 (답글 포함): " + review2.getTitle() + " (ID: " + review2.getReviewId() + ")");

        System.out.println("--- 데이터 초기화 완료 ---");
    }
}