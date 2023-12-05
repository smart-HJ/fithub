import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const URL = `https://www.googleapis.com/youtube/v3/search`;
const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;

export const useVideoStore = defineStore("video", () => {
  const selectedVideo = ref(null);
  const videos = ref([]);
  const search = (keyword) => {
    console.log(keyword)
    axios({
      url: URL,
      method: "GET",
      params: {
        key: API_KEY,
        part: "snippet",
        q: keyword,
        type: "video",
        maxResults: 3,
        order: 'viewCount',
      },
    },{
       withCredentials: true
    })
      .then((response) => {
        console.log(response);
        videos.value = response.data.items;
      })
      .catch(() => {});
  };

  const clickVideo = function (video) {
    selectedVideo.value = video;
  };

  
  return { videos, search, clickVideo,selectedVideo};
});
