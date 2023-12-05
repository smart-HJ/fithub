<template>
    <div v-if="store.selectedVideo">
    <iframe
      width="560"
      height="315"
      :src="videoURL"
      title="YouTube video player"
      frameborder="0"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
      allowfullscreen
    ></iframe>
  </div>
</template>

<script setup>
import { useVideoStore } from '../../stores/video';
import { computed } from 'vue';
const store=useVideoStore();


const videoURL=computed(()=>{
    const videoId=store.selectedVideo.id.videoId
    return `https://www.youtube.com/embed/${videoId}`
})


</script>

<style scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
}

</style>