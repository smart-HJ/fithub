<template>
    <div class="container">
        <div class="start">
            SIGNUP PAGE
        </div>
        <fieldset>
            <input type="file" @change="handleFileChange" ref="fileInput" style="display: none;">
            <div class="profile-img-box" @click="triggerFileInput">
                <img v-if="imageUrl" :src="imageUrl">
                <img v-else src="@/assets/image/addImg.jpg">
            </div>
            <label for="id">ID</label>
            <input type="text" id="id" name="id" v-model="id">
            <label for="password">PASSWORD</label>
            <input type="password" id="password" name="password" v-model="password">
            <label for="password2">CONFIRM PASSWORD</label>
            <input type="password" id="password2" name="password2" v-model="password2">
            <label for="name">NAME</label>
            <input type="text" id="name" name="name" v-model="name">
            <label for="phoneNum">phoneNumber (000-0000-0000)</label>
            <input type="text" id="phoneNum" name="phoneNum" v-model="phoneNum">
            <label for="email">EMAIL</label>
            <input type="email" id="email" name="email" v-model="email" @keyup.enter="regist">
            <button @click="regist" class="regist-btn">SIGN UP</button>
        </fieldset>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'

const store = useUserStore();

const id = ref("")
const password = ref("")
const password2 = ref("")
const name = ref("")
const email = ref("")
const img = ref("")
const phoneNum = ref("")

const users = computed(() => store.users);


onMounted(() => {
    store.setUsers();
});

const isEmailValid = (email) => {
    const re =
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
    return re.test(String(email).toLowerCase());
};

const isPasswordValid = (pwd) => {
    return pwd.length >= 8 && /[!@#$%^&*(),.?":{}|<>]/g.test(pwd);
};


const isPhoneNumValid = (phoneNum) => {
    var regExp = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;
    return regExp.test(phoneNum)
}


const regist = function () {
    if (id.value === "" ||
        password.value === "" ||
        name.value === "" ||
        email.value === ""
    ) {
        alert("모든 내용을 입력해주세요");
        return;
    }

    if (password.value !== password2.value) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    if (!isPasswordValid(password.value)) {
        alert("비밀번호는 8자리 이상이며, 특수문자를 포함해야 합니다.");
        return;
    }

    if (Array.isArray(users.value) && users.value.length > 0) {
        const userExists = users.value.some((user) => user.id === id.value);
        if (userExists) {
            alert("이미 존재하는 아이디입니다.");
            return;
        }
    }

    if (!isEmailValid(email.value)) {
        alert("올바른 이메일 형식을 입력해주세요.");
        return;
    }

    if (!isPhoneNumValid(phoneNum.value)) {
        alert("올바른 전화번호를 입력해주세요.");
        return;
    }
    const phoneNumFull =phoneNum.value.substring(0,3)+phoneNum.value.substring(4,8)+phoneNum.value.substring(9,13);

    console.log(phoneNumFull+"핸드폰번호!!!!!!!!!!!");
        store.regist(selectedFile.value, id.value, name.value, password.value, email.value,phoneNumFull)
}

// 파일 업로드
const selectedFile = ref(null)
const imageUrl = ref(null)

const handleFileChange = () => {
    selectedFile.value = event.target.files[0]
    imageUrl.value = URL.createObjectURL(selectedFile.value)
}

const fileInput = ref(null)
const triggerFileInput = () => {
    fileInput.value.click()
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

    height: 90vh;
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

.regist-btn {
    width: 45%;
    margin: 7% 0px 3%;
    padding: 6px;
    color: white;
    background-color: cadetblue;
    border: none;
    border-radius: 10px;
    box-shadow: 0px 5px 15px 1px rgb(104, 134, 112);

}

.regist-btn:hover {
    background-color: rgb(0, 87, 111);
}

.profile-img-box {
    width: 5rem;
    height: 5rem;
    border-radius: 50%;
    cursor: pointer;
    overflow: hidden;
    border: 1px solid rgb(198, 198, 198);
}

img {
    width: 100%;
}
</style>