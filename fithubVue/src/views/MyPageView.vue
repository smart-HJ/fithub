<template>
     <div class="container">

    <div class="sidebar">
      <router-link to="/mypage/" class="sidebar-item">RESERVATION</router-link>
      <router-link to="/mypage/myInfo" class="sidebar-item">INFORMATION</router-link>
      <router-link to="/mypage/myfeed" class="sidebar-item">FEED</router-link>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router=useRouter();
onMounted(()=>{
  const token = sessionStorage.getItem('access-token');
    if(token==null){
        alert('로그인이 필요합니다.')
        router.push({ name: 'login' })
    }
})
</script>

<style scoped>
*{
  font-family: 'Noto Sans KR', sans-serif;
}
.container {
  display: flex;
  padding-top: 3%;
  width: 100%;
}
.sidebar {
  display: flex;
  flex: 2;
  flex-direction: column;
  align-items: flex-start;
  padding: 10px;
  border-right: 1px solid rgb(198, 198, 198);
}
.sidebar-item {
  margin-bottom: 20px;
  padding: 10px;
  text-align: center;
  width: 100%;
  border: 2px solid rgb(231, 199, 214);
  background-color: white;
  color: #000;
  border-radius: 5px; /* 모서리 둥글게 */
  text-decoration: none; /* 밑줄 제거 */
  box-shadow: 1 1 5px 1px rgb(198, 198, 198);
}

.sidebar-item:hover {
  background-color: rgb(231, 199, 214); /* 마우스 올렸을 때의 배경색 */
}

.sidebar-item:active {
  background-color: #ced4da; /* 클릭했을 때의 배경색 */
}

.content {
  display: flex;
  flex: 8;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 10px;
}
</style>