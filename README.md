# FITHUB 🏋️‍♀ 

### 1. 프로젝트 소개
***
-  프로젝트 이름 : *FITHUB*
-  진행 기간 : 11/16-11/24 (9일)
-  크로스핏 예약, 커뮤니티, 영상 시청을 위한 웹 서비스입니다.
### 2. 프로젝트 정보

***
- SpringBoot , Vue , MySql 
- Back
   ```
   fithub
  ├── config
  │   ├── DB
  │   ├── Swagger
  │   └── Web
  ├── controller
  ├── dto
  ├── dao
  ├── socialLogin
  ├── service
  └── utils
   ```
- Front
  ```
  fithub-front
  ├── node_modules
  ├── public
  └── src
      ├── assets
      ├── components
      │   ├── board
      │   ├── common
      │   ├── mypage
      │   ├── reservation
      │   ├── user
      │   └── video
      ├── router
      ├── stores
      └── views
          ├── board
          ├── home
          ├── mypage
          ├── reservation
          ├── user
          └── video
  ```
  ### 3. 프로젝트 주요 기능
  ***
- 예약
  - 원하는 지점을 지도에서 선택할 수 있어요.
  - 원하는 날짜를 달력에서 선택할 수 있어요.
  - 예약 인원과 시간을 확인하여 결제를 하면 예약할 수 있어요.
  - 예약 취소를 할 수 있어요.
- 커뮤니티
  - 사진, 내용, 해시태그를 이용하여 게시글을 작성할 수 있어요.
  - 다른 사람의 게시글을 스크롤을 통해 조회할 수 있어요.
  - 최신순과 인기순으로 조회할 수 있어요.
  - 해시태그 검색을 통해 해시태그가 포함된 게시글을 볼 수 있어요.
  - 좋아요, 댓글 ,대댓글 기능이 있어요.
- 비디오
  - 제공되는 영상을 볼 수 있어요.

### 4. FITHUB 서비스 화면
---
#### HOME
- 로그인 할 수 있습니다.
- 이미 로그인이 되어있는 사용자라면 마이페이지로 이동할 수 있습니다. 
- 비디오 / 예약 / 커뮤니티 페이지로 이동할 수 있습니다.

<img width="654" alt="home" src="https://github.com/smart-HJ/fithub/assets/113097210/1c473265-557b-44a1-8713-6c9fb8cdd975">

#### 커뮤니티
- 다른 회원들의 게시글을 조회할 수 있습니다.
- 무한 스크롤이 적용됩니다.
- 최신순 / 인기순 정렬이 가능합니다.
- 해시태그 검색을 통해 해당 해시태그가 포함된 게시글을 조회할 수 있습니다.
- '+' 버튼으로 게시글 작성 페이지로 이동할 수 있습니다.

<img width="664" alt="listBoard" src="https://github.com/smart-HJ/fithub/assets/113097210/448e1647-33fa-4642-9480-5c50d48636d6">
<br>

#### 글쓰기
- 원하는 이미지를 첨부하고, 첨부한 이미지를 바로 확인할 수 있습니다.
- 원하는 해시태그를 게시글에 포함시킬 수 있습니다.

<img width="663" alt="writeBoard" src="https://github.com/smart-HJ/fithub/assets/113097210/8a80f31a-c8de-45b6-982e-cd60c788c9d3">
<br>

#### 게시글 상세
- 해당 게시글의 이미지, 내용, 해시태그, 댓글 및 대댓글을 조회할 수 있습니다.
- 로그인한 사용자와 게시글 작성자가 같다면, 수정 및 삭제가 가능합니다.
- 해시태그를 클릭하여 해당 해시태그가 포함된 게시글 조회가 가능합니다.
- 댓글 및 대댓글을 작성, 삭제할 수 있습니다.

<img width="669" alt="detailBoard" src="https://github.com/smart-HJ/fithub/assets/113097210/da7546e6-0729-4805-af8c-6e2cba5a6bcd">
<br>

#### 예약
- 지도에서 원하는 지점을 선택할 수 있습니다.
- 달력에서 원하는 날짜를 선택할 수 있습니다.
- 선택한 지점과 날짜에 등록되어 있는 lesson을 조회할 수 있습니다.
- 예약 버튼을 누르면 결제 후 예약이 가능합니다.
- 이미 예약한 lesson이라면 예약 취소가 가능합니다.

<img width="659" alt="reservation" src="https://github.com/smart-HJ/fithub/assets/113097210/8bf8d3e2-e78f-4fa6-99c4-44f0db703553"><br>
<img width="250" alt="mapRes" src="https://github.com/smart-HJ/fithub/assets/113097210/d9b10486-3184-459e-8d09-cf79755ea984">
<img width="200" alt="dateRes" src="https://github.com/smart-HJ/fithub/assets/113097210/78bc4f9c-4f62-45d9-819c-ea3ddca4a0d6">
<br>

#### 비디오
- 관리자가 추천하는 영상을 시청할 수 있습니다.

<img width="674" alt="video" src="https://github.com/smart-HJ/fithub/assets/113097210/4a0b760b-f88b-4d40-9c9c-19ab9a26d362">
<br>

#### 마이페이지 - FEED
- 내가 작성한 글들을 조회할 수 있습니다.

<img width="667" alt="mypageMyfeed" src="https://github.com/smart-HJ/fithub/assets/113097210/0bc7b50d-8b00-4329-8867-3dfbf7f275dd">
<br>

#### 마이페이지 - 예약
- 달력에서 예약 현황을 조회할 수 있습니다.
- 예약 취소도 가능합니다.

<img width="670" alt="mypageRes" src="https://github.com/smart-HJ/fithub/assets/113097210/c0fa28fe-acc8-48a4-a823-8f7578ca1041">
<br>

#### 마이페이지 - 내 정보
- 프로필 사진, 아이디, 비밀번호 변경이 가능합니다.
- 비밀번호 변경을 하기 위해서는 등록된 번호를 통해 문자 인증을 해야 합니다.

<img width="661" alt="mypageInfo" src="https://github.com/smart-HJ/fithub/assets/113097210/a63c0939-84c6-44ef-9e2d-1a8225edd0fc">
<br>
