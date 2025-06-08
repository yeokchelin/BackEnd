## 🏗️ 시스템 아키텍처 (System Architecture)

> 본 시스템은 확장성과 유지보수성을 고려하여 다음과 같은 아키텍처로 설계되었습니다.
>
> 각 구성 요소의 역할과 상호작용 방식을 한눈에 파악할 수 있습니다.

---

## 🛠️ 백엔드 기술 스택 (Backend Tech Stack)

> 본 시스템은 사용자 중심 설계 원칙에 따라 다음과 같은 백엔드 기술로 개발되었습니다.

*백엔드*
- [Spring Boot](https://spring.io/projects/spring-boot): 서버 로직 및 REST API 설계, 보안 처리
- Spring Security + JWT: 인증 및 인가 처리
- JPA (Hibernate): ORM 기반 데이터 관리
- [MariaDB](https://mariadb.com/): 관계형 데이터베이스
- [Lombok](https://projectlombok.org/): 코드 간결화
- [Git](https://git-scm.com/): 버전 관리

---

## 🚀 백엔드 주요 기능 (Backend Features)

- **회원 인증 및 JWT 로그인**
  - 카카오 REST API 기반 로그인
  - JWT 토큰 발급 및 인증 필터 적용

- **맛집 정보 관리**
  - 2호선 각 역 기준 맛집 정보 등록 및 조회
  - 점주 권한으로 식당, 메뉴 정보 관리 가능

- **리뷰 및 평점 시스템**
  - 예약자만 리뷰 작성 가능
  - 리뷰 목록 조회, 평점 평균 계산

- **웨이팅 시스템**
  - 사용자 → 점주에게 실시간 웨이팅 요청 등록
  - 메뉴 선택 및 방문 예정 시간 입력 포함

- **게시판 및 댓글**
  - 자유게시판, 밥친구 게시판 지원
  - 댓글 작성 및 삭제

- **회원 등급 변경**
  - 일반 회원 ↔ 점주 회원 전환 기능
  - 점주 전환 시 가게 등록 필요

---

## 📂 백엔드 폴더 구조 (Backend Directory Structure)

backend/
┣ config/ # JWT, CORS, WebSecurity 설정

┣ controller/ # REST API 진입점 (요청 처리)

┣ dto/ # 클라이언트 ↔ 서버 간 데이터 전달 객체

┣ entity/ # JPA 엔티티 클래스

┣ exception/ # 전역 예외 처리

┣ repository/ # 데이터베이스 접근 (JPA Repository)

┣ service/ # 비즈니스 로직 처리

┣ security/ # 인증 관련 필터 및 유틸리티

┗ BackendApplication.java # 메인 실행 파일

---

## 🔐 인증 및 보안 처리

- **JWT 기반 인증**
  - 로그인 성공 시 토큰 발급 → 이후 요청 시 Authorization 헤더로 인증
- **Spring Security**
  - 권한별 접근 제어 (@PreAuthorize 등)
- **CORS 정책**
  - 프론트엔드 도메인 허용 설정 포함 (개발 및 운영 환경 대응)

---

## ✅ 예시: 사용자 인증 흐름

1. 클라이언트에서 카카오 인가 코드 수신
2. 백엔드에서 카카오 API 호출 → 사용자 정보 획득
3. DB에 사용자 저장 → JWT 생성
4. JWT는 `Authorization: Bearer <token>` 헤더로 전송
5. Spring Security 필터에서 토큰 인증 후 API 접근 허용

