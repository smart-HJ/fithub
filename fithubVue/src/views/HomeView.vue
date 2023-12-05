<template>
    <div class="container">
        <div class="background-image"></div>
        <div class="background-div"></div>
        <div class="divide-container">
            <div class="left-container">
                <div class="left-contnet">
                    <div class="home-logo-box"><img src="@/assets/image/FITHUB.png"></div>
                    <div class="login-menu-box">
                        <RouterLink v-if="!loginUser" class="login-menu-btn" to="/user/login">login</RouterLink>
                        <div v-if="loginUser"><RouterLink class="login-menu-btn" to="/mypage"> MY PAGE</RouterLink></div>
                    </div>
                </div>
            </div>
            <div class="right-container">
                <div class="right-menu">
                    <RouterLink class="menu" to="/video"><img class="menu-img" src="@/assets/image/youtube.png"></RouterLink>
                    <RouterLink class="menu" to="/board/list/all"><img  class="menu-img" src="@/assets/image/board.png"></RouterLink>
                    <RouterLink class="menu" to="/reservation"><img  class="menu-img" src="@/assets/image/booking.png"></RouterLink>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import { hoursToMinutes } from 'date-fns';
import { useMyPageStore } from '@/stores/mypage';

const store = useUserStore();
const myPageStore = useMyPageStore()
function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? decodeURIComponent(match[2]) : null;
}

const loginUser = sessionStorage.getItem("loginId")

onMounted(() => {
    const token2 = getCookie("access-token");
    console.log(token2)
    if (token2 != null) {
        sessionStorage.setItem("access-token", token2);
        const token = token2.split(".");
        let id = token[1];
        id = atob(id);
        id = JSON.parse(id);
        console.log(id)
        store.loginId = id["id"];
        store.loginUserId = id["userId"];
        sessionStorage.setItem("loginUserId", id["userId"])
        sessionStorage.setItem("loginId", id["id"])
    }
    myPageStore.getMyLessons(sessionStorage.getItem('loginUserId'));
});
    
const emit = defineEmits(['logout'])

const logout = () => {
    emit
}

</script>

<style scoped>
* {
    font-family: 'Noto Sans KR', sans-serif;
}

a {
    text-decoration: none;
    color: white;
}
.container {
    margin: -5% 0;
    margin-top: -5%;
    position: relative;
    width: 100%;
    height: 90vh;
    z-index: 1;
    overflow: hidden;
    border-top: solid 50px black;
}

.divide-container {
    display: flex;
    width: 100%;
    height: 100%;

}
.left-container {
    display: flex;
    padding-bottom: 15%;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 50%;
}
.left-contnet {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: white;
    font-size: 50px;
}
.login-menu-box {
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    font-size: 1rem;
    width: 5rem;
    height: 2rem;
    border: 1px solid white;
    border-radius: 10px;
    cursor: pointer;
}
.login-menu-box div {
    width: 100%;
}
.login-menu-box div a {
    width: 100%;
    height: 100%;
}
.right-container {
    display: flex;
    justify-content: end;
    width: 50%;
}
.right-menu {
    display: flex;
    flex-direction: column;
    padding: 5% 5%;
}
.right-menu span {
    width: 35px;
    margin-bottom: 1rem;
}
.right-menu span:hover {
    cursor: pointer;
}
.right-menu span img {
    width: 100%;
}
.menu {
    margin-bottom: 20px;
    width: 35px;
}
.menu-img {
    width: 100%;
}
.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('@/assets/image/덤벨.jpg') center/cover no-repeat;
  opacity: 0.6; /*조절 가능한 투명도 값*/
  z-index: -2;
}
.background-div {
  position: absolute;
  top: 0;
  left: -45%;
  width: 100%;
  height: 100%;
  background-color: black;
  opacity: 0.9; /* 조절 가능한 투명도 값 */
  transform: skewX(-20deg);
  z-index: -1;
}

/* The rest of your existing styles */
img {
  height: 100%;
}


</style>