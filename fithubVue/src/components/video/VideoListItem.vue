<template>
    <div @click="clickVideo" class="video-item">
      <img :src="video.snippet.thumbnails.default.url" class="video-thumbnail">
      <p class="video-title">{{ video.snippet.title }}</p>
    </div>
  </template>
  
  <script setup>
  import { useVideoStore } from '@/stores/video';
  const store = useVideoStore();
  
  const props = defineProps({
    video: {
      type: Object,
      required: true,
    },
  });
  
  const clickVideo = function () {
    store.clickVideo(props.video);
  };
  </script>
  
  <style scoped>
  * {
  font-family: 'Noto Sans KR', sans-serif;
}
  .video-item {
    cursor: pointer;
    overflow: hidden;
    border-radius: 8px;
    transition: box-shadow 0.3s;
  }
  
  .video-item:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  }
  
  .video-thumbnail {
    width: 100%; /* 이미지를 부모 요소에 꽉 차도록 설정 */
    height: auto; /* 비율 유지 */
  }
  
  .video-title {
    margin-top: 10px;
    font-size: 14px; /* 원하는 크기로 조정 */
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  </style>
  