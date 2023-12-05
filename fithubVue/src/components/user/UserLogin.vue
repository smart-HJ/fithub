<template>
    <div class="container">
        <div class="start">
            SIGNIN PAGE
        </div>
        <fieldset>
            <label for="id">ID</label>
            <input type="text" id="id" name="id" v-model="id" @keyup.enter="login">
            <label for="password">PASSWORD</label>
            <input type="password" id="password" name="password" v-model="password" @keyup.enter="login">
            <button @click="login" class="login-btn">SIGN IN</button>
            <div class="social-box">
                <div class="social-icon-box">
                    <a :href="`https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${VITE_KAKAOLOGIN_API_KEY}&redirect_uri=http://localhost:8080/app/login/kakao`">
                        <img class="social-icon" src="@/assets/image/kakao-talk.png">
                    </a>
                </div>
                <div class="social-icon-box">
                    <a :href="`https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${VITE_NAVER_API_KEY}&state=STATE_STRING&redirect_uri=http://localhost:8080/app/login/naver`">
                        <img class="social-icon" src="@/assets/image/naver.png">
                    </a>
                </div>
            </div>
        </fieldset>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { ref } from 'vue';
const VITE_NAVER_API_KEY = import.meta.env.VITE_NAVER_API_KEY;
const VITE_KAKAOLOGIN_API_KEY = import.meta.env.VITE_KAKAOLOGIN_API_KEY;

const store = useUserStore();

const id = ref("")
const password = ref("")

const login = function () {
    const user = {
        id: id.value,
        password: password.value,
    }
    store.login(user)

}
</script>

<style scoped>
* {
    font-family: 'Nunito', sans-serif;
}
.container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    height: 80vh;
}
.start {
    color: rgb(0, 87, 111);
    font-size: 3rem;
    margin-bottom: 5%;
}

fieldset {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 40%;
    padding: 4% 5% 2%;

    border: 4px solid cadetblue;
    border-radius: 30px;
}

label {
    width: 100%;
}

input {
    margin-bottom: 10px;
    width: 100%;
    border: none;
    border-bottom: 2px solid cadetblue;
}

.login-btn {
    width: 45%;
    margin: 7% 0px 3%;
    padding: 6px;
    color: white;
    background-color: cadetblue;
    border: none;
    border-radius: 10px;
    box-shadow: 0px 5px 15px 1px rgb(104, 134, 112);
}

.login-btn:hover {
    background-color: rgb(0, 87, 111);
}

.social-box {
    border-top: 1px solid rgb(198, 198, 198);
    margin-top: 1rem;
    padding-top: 1rem;
    display: flex;
    justify-content: space-evenly;
    width: 50%;
}
.social-icon-box {
    width: 20%;
}

.social-icon {
    width: 100%;
}

</style>