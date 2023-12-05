<template>
    <div class="board-write-container">
        <fieldset class="write-container">
            <input type="file" @change="handleFileChange" style="display: none;" ref="boardFileInput">
            <div class="write-img-box" @click="triggerFileInput">
                <img class="yes-img" v-if="imageUrl" :src="imageUrl">
                <img v-else class="no-img" src="@/assets/image/board-add.png">
            </div>
            <textarea id="content" v-model="content" placeholder="문구 입력..."></textarea>
            <!-- 선택한 해시태그 보여주는 칸 -->
            <div class="hashtag-container">
                <span class="tags" v-for="tag, index in hashTags">
                    <div class="tags-img-box"><img src="@/assets/image/hashtag.png"></div>
                    {{ tag }}
                    <button @click="deleteHashTag(index)">X</button>
                </span>
            </div>
        </fieldset>
        <!-- 여기부터 해시태그 -->
        <div class="regist-menu">
            <label for="hashTag"><div><img src="@/assets/image/hashtag.png"></div></label>
            <input type="text" id="hashTag" v-model.trim="hashTag" @keyup.enter="hashTagRegist">
            <button class="hashtag-regist-btn" @click="hashTagRegist">추가</button>
        </div>
        <button class="regist-btn" @click="regist">등록</button>
    </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '../../stores/user';
import { useRouter } from 'vue-router';
const store = useBoardStore()
const userStore=useUserStore();
const userId = userStore.loginUserId
const content = ref('')


const router=useRouter();
onMounted(()=>{
  const token = sessionStorage.getItem('access-token');
    if(token==null){
        alert('로그인이 필요합니다.')
        router.push({ name: 'login' })
    }
})

// 파일 업로드
const selectedFile = ref(null)
const imageUrl = ref(null)

const handleFileChange = () => {
    selectedFile.value = event.target.files[0]
    imageUrl.value = URL.createObjectURL(selectedFile.value)
}

const regist = () => {

    if(!selectedFile.value){
        console.log('파일이 없어용')
        return
    }
    store.regist(selectedFile.value, userId, content.value, JSON.stringify(hashTags.value))
}

//해시태그
const hashTag = ref('');
const hashTags = ref([]);

const deleteHashTag = function (index) {
    console.log(index)
    hashTags.value.splice(index, 1)
    console.log(hashTags.value)
}

const hashTagRegist = function () {
    if (hashTags.value.some((tag) => tag === hashTag.value)) {
        hashTag.value='';
        alert('이미 등록된 해시태그입니다.')
        return ;
    }
    if(hashTag.value==''){
        alert('아무 내용이 없습니다.')
        return;
    }
    // hashTags 배열에 새로운 태그 추가
    hashTags.value.push(hashTag.value);
    console.log(hashTags.value)

    // 추가한 후에 입력 필드 초기화
    hashTag.value = '';
}
const boardFileInput=ref(null)
const triggerFileInput = () => {
    boardFileInput.value.click()
}

</script>

<style scoped>

.board-write-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.write-container {
    margin-top: 3%;
    padding: 2rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 70vh;
    width: 40%;
    box-shadow: 0 0 10px 5px rgb(198, 198, 198);
}
.write-img-box {
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    width: 100%;
    height: 20rem;
    border: 1px solid rgb(198, 198, 198);
}
.yes-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.no-img {
    width: 10%;
    height: 10%;
    opacity: 50%;
}
textarea {
    font-family: 'Noto Sans KR', sans-serif;
}
.write-container textarea {
    margin-top: 1rem;
    padding: 5px;
    resize: none;
    width: 90%;
    height: 10rem;
    min-height: 100%;
}
.tags-img-box {
    display: flex;
    height: 1rem;
}
.tags-img-box img {
    height: 100%;
}
.hashtag-container {
    display: flex;
    flex-wrap: wrap;
    margin-top: 1rem;
    width: 100%;
    height: 100%;
    overflow-y: auto;
    font-family: 'Black Han Sans', sans-serif;
}
.tags {
    display: flex;
    align-items: center;
}
.tags button {
    text-align: center;
    background-color: white;
    border: none;
    font-size: 10px;
}

/* 스크롤바 ㅎ */
::-webkit-scrollbar-track {
  background-color: white; /* 트랙 배경색 */
}
::-webkit-scrollbar {
  width: 5px; /* 스크롤바 너비 */
}
::-webkit-scrollbar-thumb {
  background-color: cadetblue; /* 스크롤 핸들 색상 */
  /* border-radius: 3px; 스크롤 핸들 모서리의 둥글기 */
}
.regist-menu {
    margin-top: 1.5rem;
}
.regist-menu input {
    font-family: 'Black Han Sans', sans-serif;
}
.regist-menu button {
    font-family: 'Noto Sans KR', sans-serif;
}
.regist-menu div {
    width: 1rem;
}
.regist-menu img {
    width: 100%;
}
.regist-menu input {
    border: none;
    border-bottom: 2px solid black;
}
.hashtag-regist-btn {
    border: none;
    background-color: white;
}
.regist-btn {
    color: white;
    padding: 0.4rem 1rem;
    margin-top: 1rem;
    border: none;
    background-color: cadetblue;border-radius: 10px;
    box-shadow: 0px 5px 10px 1px rgb(104, 134, 112);
    font-family: 'Noto Sans KR', sans-serif;
}
.regist-btn:hover {
    background-color: white;
    color: black;
    border: 1px solid cadetblue;
}
</style>