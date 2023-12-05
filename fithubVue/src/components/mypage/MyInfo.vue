<template>
    <div class="info-container">
        <div class="info-title">
            MY INFO
        </div>
        <fieldset>
            <input type="file" @change="handleFileChange" ref="fileInput" style="display: none;">
            <div class="profile-img-box" @click="triggerFileInput">
                <img v-if="!store.user.img" src="http://localhost:8080/image/noImg.png">
                <img v-if="store.user.img && !changeImg" :src="'http://localhost:8080/image/profile/' + store.user.img">
                <img v-if="store.user.img && changeImg" :src="imageUrl">
            </div>
            
                <label for="id">ID</label>
                <input type="text" id="id" name="id" v-model="store.user.id" @change="validChkId">
                <label for="name">NAME</label>
                <input type="text" id="name" name="name" v-model="store.user.name" disabled>
                <button @click="sendMessage">비밀번호 변경</button>
               
                <div class="pw-update-box" v-if="change">
                    <div>회원가입시 핸드폰 번호로 인증번호를 발송했습니다.</div>
                    <div class="pw-update-confirm">인증번호 입력 : <input type="text" v-model="inputNum" @keyup.enter="checkSame"><button @click="checkSame">인증번호 입력</button></div>
                </div>
    
                <div class="modal-wrap" v-show="modalCheck" @click="modalOpen">
                    <div class="modal-container" @click.stop>
                        <div>
                            <label for="password">NEW PASSWORD</label>
                            <input type="password" id="password" name="password" v-model="password">
                            <label for="password2">CONFIRM PASSWORD</label>
                            <input type="password" id="password2" name="password2" v-model="password2">
                        </div>
                        <div class="modal-btn">
                            <button @click="modalOpen">닫기</button>
                            <button @click="changePassword">확인</button>
                        </div>
                    </div>
                </div>
                <label for="email">EMAIL</label>
                <input type="email" id="email" name="email" v-model="store.user.email" disabled>
                <button @click="updateInfo" class="update-btn">정보 수정</button>
        </fieldset>
    </div>
</template>

<script setup>
import axios from 'axios';

import { ref, computed, onMounted } from 'vue'
import { useMyPageStore } from '@/stores/mypage'
import { useUserStore } from '@/stores/user';

const store = useMyPageStore();
const userStore = useUserStore()
const users = computed(() => userStore.users);

const loginUserId = sessionStorage.getItem('loginUserId')
console.log(loginUserId)
onMounted(() => {
    store.getUser(loginUserId)
    userStore.setUsers();
})

// 프로필 사진 파일
const selectedFile = ref(null)
const imageUrl = ref(null)
const changeImg = ref(false)

const handleFileChange = () => {
    changeImg.value = true
    selectedFile.value = event.target.files[0]
    imageUrl.value = URL.createObjectURL(selectedFile.value)
    store.user.img = selectedFile.value['name']
}

const fileInput = ref(null)
const triggerFileInput = () => {
    fileInput.value.click()
}


const updateInfo = () => {
    // 아이디 중복체크하기
    if (store.user.id == "") {
        alert('아이디를 입력해 주세요')
        return
    }
    if (Array.isArray(users.value) && users.value.length > 0 && store.user.id !== sessionStorage.getItem('loginId')) {
        const userExists = users.value.some((user) => user.id === store.user.id);
        if (userExists) {
            console.log(userExists)
            alert("이미 존재하는 아이디입니다.");
            return;
        }
    }
    store.updateInfo(selectedFile.value, store.user.userId, store.user.id)
}

//비밀번호변경 문자 발송
const change = ref(false);
const ranSu = ref('');
const inputNum = ref('')
const sendMessage = function () {
    const phoneNumber = store.user.phoneNum
    axios.get(`http://localhost:8080/sendMessage/msg?phoneNumber=${phoneNumber}`).then((res) => {
        change.value = !change.value
        ranSu.value = res.data
    })
}

const checkSame = function () {
    if (ranSu.value == inputNum.value) {
        alert('인증 성공!')
        modalOpen()
    } else {
        alert('인증 번호 불일치!')
    }

}
const password = ref("")
const password2 = ref("")

const isPasswordValid = (pwd) => {
    return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};

const changePassword = function () {
    if (password.value !== password2.value) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    if (!isPasswordValid(password.value)) {
        alert("비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.");
        return;
    }
    store.changePassword(password.value,store.user.userId)
    change.value = !change.value
    modalOpen()
   
}
const modalCheck = ref(false);

const modalOpen = function () {
    modalCheck.value = !modalCheck.value
}
</script>

<style  scoped>

.info-container {
    display: flex;
    padding-bottom: 30%;
    flex-direction: column;
    width: 60%;
    height: 50vh;
}
.info-title {
    font-size: 1.5rem;
    text-align: center;
}

fieldset {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}
fieldset label, fieldset input, fieldset button {
    width: 80%;
    margin-bottom: 0.7rem;
}

fieldset button {
    background-color: white;
}

fieldset button:hover {
    color: rgb(86, 86, 86);
    border: 2px solid rgb(231, 199, 214);
    background-color: rgb(231, 199, 214);
}



.profile-img-box {
    margin-top: 2rem;
    height: 8rem;
    width: 8rem;
    border-radius: 50%;
    cursor: pointer;
    overflow: hidden;
    border: 1px solid rgb(198, 198, 198);
}

img {
    width: 100%;
}

.pw-update-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.pw-update-confirm {
    width: 50%;
}
.pw-update-box input, .pw-update-box button {
    width: 100%;
}

.modal-wrap {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.4);
}

/* modal or popup */
.modal-container {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 550px;
    background: #fff;
    border-radius: 10px;
    padding: 20px;
    box-sizing: border-box;
}
.modal-container label, .modal-container input, .modal-container button {
    width: 100%;
}
</style>