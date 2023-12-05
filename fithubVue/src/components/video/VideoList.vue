<template>
  <div class="container">
    <div class="sidebar">
      <button @click="search('크로스핏 루틴 추천')">운동 루틴 추천</button>
      <button @click="search('운동자세교정영상')">자세 교정</button>
      <button @click="search('홈트유산소추천')">집에서 하는 유산소 운동</button>
      <button @click="search('폼롤러 사용 스트레칭')">운동 후 스트레칭</button>
    </div>
    <div class="content">
      <div class="title-box">RECOMMENDED VIDEO</div>
      <div class="video-detail">
        <VideoDetail />
      </div>
      <div class="video-list">
        <VideoListItem v-for="video in store.videos" :key="video.id.videoId" :video="video" />
      </div>
    </div>
  </div>
</template>
  
<script setup>
import { useVideoStore } from '@/stores/video';
import VideoListItem from './VideoListItem.vue';
import { onMounted, onUpdated } from 'vue';
import VideoDetail from './VideoDetail.vue';

const store = useVideoStore();

const search = (keyword) => {
  console.log(keyword);
  store.search(keyword);
};

onMounted(() => {
  store.search('크로스핏 루틴 추천');
});

onUpdated(() => {
  store.clickVideo(store.videos[0]);
});
</script>

<style scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
}
.container {
  display: flex;
  font-family: 'Arial', sans-serif;
}

.sidebar {
  display: flex;
  flex: 2;
  flex-direction: column;
  align-items: flex-start;
  padding: 10px;
  border-right: 1px solid rgb(198, 198, 198);
}

.sidebar button {
  background-color: #fff;
  width: 100%;
  color: #1a936f;
  border: none;
  box-shadow: 1px 1px 1px 1px rgb(198, 198, 198);
  padding: 10px;
  margin-bottom: 10px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.sidebar button:hover {
  background-color: #eee;
  color: #1a936f;
}

.content {
  display: flex;
  flex: 8;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 10px;
}

.title-box {
  margin-bottom: 2rem;
  font-size: 1.3rem;
  background: linear-gradient(to top, cadetblue, 10%, transparent 10%);
}

.video-detail {
  margin-bottom: 2rem;
}

.video-list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  /* 각 아이템 사이의 간격 조정 */
}

/* VideoListItem 스타일 추가 */
.video-list .video-item {
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  transition: box-shadow 0.3s;
}

.video-list .video-item:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}</style>
