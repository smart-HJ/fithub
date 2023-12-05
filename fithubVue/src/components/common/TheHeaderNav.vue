<template>
    <div>
        <header>
            <nav>
                <div class="logo-box">
                    <RouterLink to="/"><img src="@/assets/image/FITHUB.png"></RouterLink>
                </div>
                <div>
                    <RouterLink to="/video">VIDEO | </RouterLink>
                    <RouterLink to="/board/list/all">BOARD | </RouterLink>
                    <RouterLink to="/reservation"> BOOKING</RouterLink>
                </div>
                <div>
                    <span v-if="!loginUser">
                        <RouterLink to="/user/regist">JOIN | </RouterLink>
                        <RouterLink to="/user/login">SIGNIN</RouterLink>
                    </span>
                    <span class="logout-btn" v-else @click="logout">LOGOUT |</span>
                    <span v-if="loginUser"><RouterLink to="/mypage"> MY PAGE</RouterLink></span>
                </div>
            </nav>
        </header>
        <RouterView />
    </div>
</template>

<script setup>

import { ref, computed, onMounted } from 'vue'
import axios from 'axios';
import router from '@/router'
import { useUserStore } from '../../stores/user';
import Cookies from 'js-cookie';

const store = useUserStore()
const loginUser = ref(sessionStorage.getItem('access-token'))

const updateLoginUser = () => {
    loginUser.value = sessionStorage.getItem('access-token')
}

function getCookie(name) {
    const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    return match ? decodeURIComponent(match[2]) : null;
}

function kakaoLogout() {
    axios({
        method: 'POST',
        url: 'https://kapi.kakao.com/v1/user/logout',
        headers: {
            Authorization: `Bearer ${getCookie("K_accessToken")}`,
        },
    }).then((res) => {
        console.log(res.data);
        Cookies.remove('K_accessToken');
        window.location.href = 'https://kauth.kakao.com/oauth/logout?client_id=792a4d3c522869517242e11ef2d04e8a&logout_redirect_uri=http://localhost:8080/app/login/kakao/logout';
    }).catch((error) => {
        console.error(error);
    });
}

function NaverLogout() {
    const naverCookie=getCookie("N_accessToken");
    Cookies.remove('N_accessToken');
    axios({
        method: 'POST',
        url: 'https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=RoTm9DndQKTXt_RvtSWU&client_secret=OccnHkPViW&access_token='+naverCookie+'&service_provider=NAVER',
        headers: {
            Authorization: `Bearer ${naverCookie}`,
        },
    }).then((res) => {
    }).catch((error) => {
        console.error(error);
    });
}
const logout = () => {
    axios.get(`http://localhost:8080/api-user/logout`)
        .then((res) => {
            if (getCookie("K_accessToken") != null) {
                kakaoLogout();
            }
            else if (getCookie("N_accessToken") != null) {
                NaverLogout();
            }
            Cookies.remove('access-token');
            sessionStorage.removeItem("access-token")
            sessionStorage.removeItem("loginUserId")
            sessionStorage.removeItem("loginId")
            updateLoginUser()
            store.loginId = ''
            store.loginUserId = ''
            router.push({ name: 'home' })
        })
}

//sessionStorage
const beforeEnter = (to, from, next) => {
    updateLoginUser()
    const isAuthenticated = !!sessionStorage.getItem('access-token');
    if (to.path === '/user/login' && isAuthenticated) {
        next('/');
    } else if (to.path === '/board/write' && !isAuthenticated) {
        next('/user/login')
    } else {
        next();
    }
};

onMounted(() => {
    router.beforeEach(beforeEnter);
});

</script>
<style  scoped>


a {
    color: white;
    text-decoration: none;
}
.logout-btn {
    color: white;
    cursor: pointer;
}
nav {
    margin: 1rem;
    display: flex;
    justify-content: space-between;
    font-family: 'Dongle', sans-serif;
    font-size: 1.5rem;
}

header {
    background-color: black;
}
.logo-box {
    width: 4rem;
    
}
.logo-box img {
    width: 100%;
}
</style>