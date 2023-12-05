<template>
    <div class="container" @click="removeDeleteBtn">
        <div class="div-container">
            <div class="left-container">
                <img class="detailImg" :src="'http://localhost:8080/image/board/' + store.board.img" alt="Board Image">
            </div>
            <div class="right-container">
                <div class="profile">
                    <div>
                        <img class="profile-image" v-if="store.board.profileImg"
                            :src="'http://localhost:8080/image/profile/' + store.board.profileImg" alt="Profile Image">
                        <img class="profile-image" v-else src="http://localhost:8080/image/noImg.png" alt="No Image">
                        <span>{{ store.board.id }}</span>
                    </div>
                    <div class="cnt-box">
                        <div>
                            <span> {{ store.board.likeCnt }}</span>
                            <span v-if="store.board.islike > 0" @click="cancelLike"><img class="islike-img"
                                    src="@/assets/image/heart.png"></span>
                            <span v-else @click="like"><img class="islike-img" src="@/assets/image/no-heart.png"></span>
                        </div>
                        <div>
                            <span> {{ store.board.commentCnt }}</span>
                            <span> <img class="reply-btn" src="@/assets/image/message.png"></span>
                        </div>
                    </div>
                </div>
                <div class="content-container"> {{ store.board.content }}</div>
                <div class="hashtag-container">
                    <span class="hashtag-box" v-for="hashTag in hashTags" @click="search(hashTag)">
                        <img src="@/assets/image/hashtag.png">{{ hashTag }}</span>
                </div>
                <div class="comment-box">
                    <div class="comment-container" v-for="comment in comments" :key="comment.commentId">
                        <span class="iscocoment-img" v-if="comment.cocomment == 'Y'">
                            <img src="@/assets/image/reply.png">
                        </span>
                        <span>
                            <img class="profile-image" v-if="comment.img"
                                :src="'http://localhost:8080/image/profile/' + comment.img">
                            <img class="profile-image" v-else src="http://localhost:8080/image/noImg.png">
                        </span>
                        <span>
                            <span>{{ comment.id }}</span>
                            <!-- <span class="comment-box"> -->
                            <span class="comment-content" @dblclick="showDeleteBtn = comment.commentId"
                                @click.stop="removeDeleteBtn">{{ comment.content }}</span>
                            <span v-if="showDeleteBtn === comment.commentId && loginUserId == comment.userId"
                                @click="deleteSomething(comment.cocomment, comment.commentId, comment.commentGroup)"
                                class="delete-box">
                                <img src="@/assets/image/remove.png">
                            </span>
                            <!-- </span> -->
                            <!-- <span>그룹 : {{ comment.commentGroup }}</span> -->
                            <!-- <span>대댓글인지? : {{ comment.cocomment }}</span> -->
                            <button class="reply-btn" v-if="comment.cocomment == 'N'"
                                @click="cancelCoco(comment.commentGroup)">
                                <img src="@/assets/image/message.png">
                            </button>
                        </span>
                    </div>
                </div>
                <div class="input-container">
                    <div v-if="cocoFlag" class="cocoment-box">
                        <label for="coco">답글 남기는 중</label>
                        <button type="button" @click="coFlagcon" id="coco">x</button>
                    </div>
                    <div class="input-box">
                        <input ref="myInput" type="text" v-model="commentInput" @keyup.enter="registComment">
                        <span @click="registComment">게시</span>
                    </div>
                </div>
            </div>
        </div>
        <div class="button-container">
            <button v-if="loginUserId && loginUserId == store.board.userId" @click="updateBoard">수정</button>
            <button v-if="loginUserId && loginUserId == store.board.userId" @click="deleteBoard">삭제</button>
        </div>
    </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useUserStore } from '../../stores/user';

const store = useBoardStore()
const userStore = useUserStore()
const route = useRoute()
const router = useRouter()

const loginUserId = userStore.loginUserId

const boardId = route.params.id

onMounted(() => {

    store.getBoard(boardId, loginUserId)
    setComment(boardId)
    setHashTag(boardId)
})

const myInput = ref(null)
const focusOnInput = () => {
    myInput.value.focus()
}


const updateBoard = () => {
    router.push({ name: 'update' })
}

// 게시글 삭제
const deleteBoard = () => {
    console.log(route.params.id)
    axios.delete(`http://localhost:8080/api-board/delete/${route.params.id}`)
        .then((res) => {
            router.push({ name: 'list', params: { tag: 'all' } })
        })
}

// 댓글
const CommentAPI = `http://localhost:8080/api-comment`;
const comments = ref([])

const cocoFlag = ref(false) // false가 원본댓
const commentInput = ref('')

// 댓글 불러오기.
const setComment = (boardId) => {
    axios.get(`${CommentAPI}/comments/${boardId}`)
        .then((res) => {
            comments.value = res.data
        })
}
const BoardAPI = `http://localhost:8080/api-board`;
const hashTags = ref([])

//해시태그 불러오기.
const setHashTag = (boardId) => {
    axios.get(`${BoardAPI}/hashTags/${boardId}`).then((res) => {
        hashTags.value = res.data
    })
}

//해시태그 누르면 그 리스트 띄우기 여기 보다가 끝남 
const search = function (tag) {
    router.push({ name: 'list', params: { tag: tag } })
}

const coFlagcon = () => {
    cocoFlag.value = false
    focusOnInput()
}

// commentGroup 가장 큰 거 찾을거야
const sortedArray = computed(() => {
    if (comments.value.length > 0)
        return comments.value.slice().sort((a, b) => b.commentGroup - a.commentGroup);
    return 0
});

const lastGroup = computed(() => {
    return sortedArray.value.length > 0 ? sortedArray.value[0].commentGroup : null;
});

const commentGroup = ref('')
// 대댓글달기 취소
const cancelCoco = (group) => {
    focusOnInput()
    cocoFlag.value = true
    commentGroup.value = group
    // console.log(group)
    // console.log(cocoFlag.value)
    // console.log(store.board)
}

// 댓글 달기
const registComment = () => {
    const token = sessionStorage.getItem('access-token');
    if (token == null) {
        alert('로그인이 필요합니다.')
        router.push({ name: 'login' })
    }

    const comment = {
        boardId: route.params.id,
        userId: loginUserId,
        content: commentInput.value,
        commentGroup: cocoFlag.value ? commentGroup.value : lastGroup.value + 1,
        cocomment: cocoFlag.value ? 'Y' : 'N'
    }
    axios.post(`${CommentAPI}/regist`, comment)
        .then((res) => {
            comments.value = [...comments.value, res.data]
            setComment(route.params.id)
            commentInput.value = ''
            cocoFlag.value = false
            store.board.commentCnt++
        })
}

// 댓글 삭제
const showDeleteBtn = ref(false)
const deleteSomething = (cocomment, commentId, group) => {
    console.log(route.params.id)
    console.log(cocomment)
    console.log(commentId)
    console.log(group)
    axios.delete(`${CommentAPI}/delete?commentId=${commentId}&cocomment=${cocomment}&boardId=${route.params.id}&commentGroup=${group}`)
        .then((res) => {
            setComment(route.params.id)
            store.board.commentCnt--
        })
    showDeleteBtn.value = null
}
const removeDeleteBtn = () => {
    showDeleteBtn.value = null
}

const LikeAPI = `http://localhost:8080/api-like`;
//좋아요
const like = function () {

    const token = sessionStorage.getItem('access-token');
    if (token == null) {
        alert('로그인이 필요합니다.')
        router.push({ name: 'login' })
    }

    axios.get(`${LikeAPI}/regist?userId=${userStore.loginUserId}&boardId=${boardId}`)
        .then((res) => {
            console.log(res)
            store.board.likeCnt++;
            store.board.islike++;
        })
}

//좋아요취소
const cancelLike = function () {
    axios.delete(`${LikeAPI}/delete?userId=${userStore.loginUserId}&boardId=${boardId}`)
        .then((res) => {
            console.log(res)
            store.board.likeCnt--;
            store.board.islike--;
        })
}



</script>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
}

.div-container {
    padding: 10% 10% 3%;
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 80vh;
}

.left-container {
    display: flex;
    width: 48%;
}

.detailImg {
    width: 100%;
}

.right-container {
    display: flex;
    flex-direction: column;
    width: 48%;
}

.profile {
    display: flex;
    justify-content: space-between;
    font-family: 'Noto Sans KR', sans-serif;
}

.profile-image {
    width: 25px;
    height: 25px;
    border-radius: 50%;
    margin-right: 0.3rem;
}
.cnt-box {
    display: flex;
    flex-direction: column;
    align-items: end;
}
.comment-box {
    height: auto;
    overflow-y: auto;
}

.comment-container {
    position: relative;
    margin-bottom: 1rem;
    font-family: 'Noto Sans KR', sans-serif;
}
/* 스크롤바 ㅎ */
::-webkit-scrollbar-track {
  background-color: white; /* 트랙 배경색 */
}
::-webkit-scrollbar {
  width: 5px; /* 스크롤바 너비 */
}
::-webkit-scrollbar-thumb {
  background-color: black; /* 스크롤 핸들 색상 */
  /* border-radius: 3px; 스크롤 핸들 모서리의 둥글기 */
}

.islike-img {
    margin-left: 5px;
    margin-right: 4px;
    width: 1.5rem;
}

.hashtag-container {
    margin-bottom: 0.4rem;
    border-bottom: 1px solid rgb(198, 198, 198);
    font-family: 'Black Han Sans', sans-serif;
}

.hashtag-box img {
    width: 1rem;
}

.content-container {
    margin: 0.5rem 0;
    padding-left: 3px;
    font-family: 'Noto Sans KR', sans-serif;
}

.iscocoment-img img {
    width: 1rem;
    margin-right: 0.2rem;
}

.comment-content {
    margin-left: 0.3rem;
}

.delete-box {
    position: absolute;
    right: 10%;
    height: 100%;
    border-radius: 50%;
    top: 50%;
    transform: translateY(-50%);
}

.delete-box img {
    height: 100%;
}

.reply-btn {
    float: right;
    width: 2rem;
    padding: 0;
    background-color: white;
    border: none;

}

.reply-btn img {
    width: 100%;
}

.input-container {
    display: flex;
    flex-direction: column;
    margin-top: auto;
}

.cocoment-box {
    font-size: 0.7rem;
    display: flex;
    padding-left: 0.5rem;
}

.cocoment-box label:hover {
    cursor: pointer;
}

.input-container button {
    background-color: white;
    border: none;
}

.input-box input {
    width: 100%;
    height: 2rem;
    padding-left: 1rem;
    border-radius: 20px;
    border: 1px solid black;
}

.input-container span {
    float: right;
}

.input-box {
    position: relative;
    font-family: 'Noto Sans KR', sans-serif;
}

.input-box span {
    position: absolute;
    font-size: 0.8rem;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
}

.input-box span:hover {
    cursor: pointer;
}

.button-container {
    display: flex;
    justify-content: center;
    font-family: 'Noto Sans KR', sans-serif;
}

.button-container button {
    margin-right: 1rem;
    width: 5rem;
    padding: 6px;
    background-color: white;
    border: 2px solid cadetblue;
    color: black;
    border-radius: 10px;
    box-shadow: 0px 5px 15px 1px rgb(104, 134, 112);
}

.button-container button:hover {
    color: white;
    background-color: cadetblue;
    border: 2px solid cadetblue;
}
</style>