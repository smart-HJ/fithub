<template>
  <div class="board-item-container">
    <div class="board-item">
      <div class="board-info">
        <div class="board-img-box">
          <img class="board-image" :src="`http://localhost:8080/image/board/${board.img}?${randomdate}`" alt="Board Image">
        </div>
        <div class="profile-section">
          <img class="profile-image" v-if="board.profileImg" :src="'http://localhost:8080/image/profile/' + board.profileImg"
            alt="Profile Image">
          <img class="profile-image" v-else src="http://localhost:8080/image/noImg.png" alt="No Image">
          <div class="board-author">{{ board.id }}</div>
        </div>
        <!-- <div class="board-content">{{ board.content }}</div> -->
        <!-- <div class="board-date">{{ board.regDate }}</div> -->
      </div>
    </div>
    <div class="board-item-effect" @click="goItemDetail">
      <div class="effect-box">
        <div class="like-img-box"><img class="like-img" src="@/assets/image/heart.png">
          <div class="likeCnt">{{ board.likeCnt }}</div>
        </div>
        <div class="like-img-box"><img class="like-img" src="@/assets/image/comment2.png">
          <div class="likeCnt">{{ board.commentCnt }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

const randomdate = Date.now()

const props = defineProps({
  board: {
    type: Object,
    required: true,
  },
})

const goItemDetail = () => {
  router.push({ name: 'detail', params: { id: props.board.boardId } })
}

</script>

<style scoped>
* {
  font-family: 'Nunito', sans-serif;
}

.board-item-container {
  position: relative;
  border: 1px solid #ddd;
  height: 45vh;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  overflow: hidden;
  flex-basis: calc(25% - 32px);
  cursor: pointer;
}
.board-item-container:hover .board-item-effect {
  display: flex;
  opacity: 70%;
}
.board-item-effect{
  display: none;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgb(0, 0, 0);
  width: 100%;
  height: 100%;
  color: white;
}

.like-img-box {
  display: flex;
  width: 20px;
}
.likeCnt {
  margin-left: 4px;
}
.like-img {
  width: 100%;
}
.board-item {
  height: 100%;
}
.board-info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  padding: 16px;
}
.board-img-box {
  height: 90%;
  overflow: hidden;
}
.board-image {
  width: 100%;
  height: auto;
  
}

.profile-section {
  display: flex;
  align-items: center;
  gap: 8px;
}

.profile-image {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

.board-author,
.board-content,
.board-date {
  margin-bottom: 8px;
}</style>
