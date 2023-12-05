<template>
    <div class="board-update-container">
        <fieldset class="update-container">
            <!-- <div> 글쓴이(확인용): {{ store.board.id }}</div> -->
            <div class="update-img-box">
                <img :src="'http://localhost:8080/image/board/' + store.board.img">
            </div>
            <textarea id="content" v-model="store.board.content"></textarea>
            <div class="hashtag-container">
                <span class="tags" v-for="tag, index in hashTags" @click="deleteHashTag(index)">
                    <div class="tags-img-box"><img src="@/assets/image/hashtag.png"></div>
                    {{ tag }}
                    <button @click="deleteHashTag(index)">X</button>
                </span>
            </div>
        </fieldset>
        <div class="update-menu">
            <label for="hashTag"><div><img src="@/assets/image/hashtag.png"></div></label>
            <input type="text" id="hashTag" v-model.trim="hashTag" @keyup.enter='hashTagRegist'>
            <button class="hashtag-regist-btn" @click="hashTagRegist">추가</button>
        </div>
        <button class="update-btn" @click="updateBoard">수정</button>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useBoardStore } from '@/stores/board';
import axios from 'axios'

const store = useBoardStore()

console.log(store.board)

const updateBoard = () => {
    store.board.hashTags = hashTags.value
    store.updateBoard(store.board)
}

onMounted(() => {
    setHashTag(store.board.boardId)
})

const hashTag = ref('');
const hashTags = ref([])

//해시태그 불러오기.
const setHashTag = (boardId) => {
    axios.get(`http://localhost:8080/api-board/hashTags/${boardId}`)
        .then((res) => {
            console.log(res)
            if(res.data != '')
                hashTags.value = res.data
    })
}
// 해시태그 추가
const hashTagRegist = function () {
    console.log(hashTag)
    if(hashTag.value==[]){
        alert('아무 내용이 없습니다.')
        return;
    }
    if (hashTags.value.some((tag) => tag === hashTag.value)) {
        hashTag.value='';
        alert('이미 등록된 해시태그입니다.')
        return ;
    }
    hashTags.value.push(hashTag.value);

    hashTag.value = '';
}
// 해시태그 삭제
const deleteHashTag = (index) => {
    hashTags.value.splice(index, 1)
}

</script>

<style scoped>
.board-update-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.update-container {
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
.update-img-box {
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
    width: 100%;
    height: 20rem;
    border: 1px solid rgb(198, 198, 198);
}
.update-img-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.update-container textarea {
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
}
.tags {
    display: flex;
    align-items: center;
    font-family: 'Black Han Sans', sans-serif;
}
.tags:hover {
    cursor: pointer;
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
.update-menu {
    margin-top: 1.5rem;
}
.update-menu input {
    font-family: 'Black Han Sans', sans-serif;

}
.update-menu div {
    width: 1rem;
}
.update-menu img {
    width: 100%;
}
.update-menu input {
    border: none;
    border-bottom: 2px solid black;
}
.hashtag-regist-btn {
    border: none;
    background-color: white;
    font-family: 'Noto Sans KR', sans-serif;
}
.update-btn {
    color: white;
    padding: 0.4rem 1rem;
    margin-top: 1rem;
    border: none;
    background-color: cadetblue;border-radius: 10px;
    box-shadow: 0px 5px 10px 1px rgb(104, 134, 112);
    font-family: 'Noto Sans KR', sans-serif;
}
.update-btn:hover {
    background-color: white;
    color: black;
    border: 1px solid cadetblue;
}
</style>