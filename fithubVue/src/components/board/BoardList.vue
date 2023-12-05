<template>
    <div class="boards-container">
        <div class="board-header">
            <div class="board-left-menu">
                <div class="board-title">MOMENT</div>
                <div>
                    <button class="sort-btn" @click="orderByregDate('regDate')">최신순</button>
                    <button class="sort-btn" @click="orderBylikeCnt('likeCnt desc')">인기순</button>
                </div>
            </div>
            <div class="board-right-menu">
                <div class="write-box">
                    <RouterLink to="/board/write"><img src="@/assets/image/board-add.png"></RouterLink>
                </div>
                <div>
                    <label for="tag"><img src="@/assets/image/hashtag.png"></label>
                    <input type="text" id="tag" v-model.value.trim="tag" @keyup.enter="search">
                    <button @click="search"><img src="@/assets/image/magnifier.png"></button>
                </div>
            </div>
        </div>
        <div class="item-container" id="item-container">
            <BoardListItem v-for="board in visibleBoards" :key="board.boardId" :board="board" />
        </div>
    </div>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { ref, computed,onMounted ,onUpdated, watch} from 'vue';
import BoardListItem from './BoardListItem.vue';
import { useRoute } from 'vue-router'
const route = useRoute()
const store = useBoardStore()

onMounted(() => {
    store.search(route.params.tag);
});

watch(() => store.boards, () => {
  setVisibleData();
});

// 최신순 정렬
const orderByregDate = (key) => {
    console.log(key)
    const sortedBoards = computed(() => {
        if (store.boards.length > 0)
            return store.boards.slice().sort((a, b) => new Date(b.regDate) - new Date(a.regDate));
        return []
    })
    store.setSortedBoard(sortedBoards.value)
    visibleBoards.value.splice(0, visibleBoards.value.length, ...sortedBoards.value.slice(0, page * pageSize))
    // removeDuplicateKeys()
    console.log(store.boards)
}
// 인기순 정렬
const orderBylikeCnt = (key) => {
    console.log(key)
    const sortedBoards = computed(() => {
        if (store.boards.length > 0)
        return store.boards.slice().sort((a, b) => b.likeCnt - a.likeCnt);
    return []
    })
    store.setSortedBoard(sortedBoards.value)
    visibleBoards.value.splice(0, visibleBoards.value.length, ...sortedBoards.value.slice(0, page * pageSize))
    // removeDuplicateKeys()
    console.log(store.boards)
}

// 중복된 키 제거 함수
const removeDuplicateKeys = () => {
  const uniqueKeys = new Set();
  visibleBoards.value = visibleBoards.value.filter((board) => {
    if (!uniqueKeys.has(board.boardId)) {
      uniqueKeys.add(board.boardId);
      return true;
    }
    return false;
  });
};


//해시태그검색
const tag=ref('')

const search=function(){
    if(tag.value == ''){
        visibleBoards.value = []
        store.setBoard()
        page=1
        return
    }
    visibleBoards.value = []
    store.search(tag.value)
    page=1
    tag.value = ''
}
// 라우터 매개변수 변경 감시
watch(() => route.params.tag, (newTag, oldTag) => {
    visibleBoards.value = []
    page = 1
    store.search(newTag);
});

// 인피니트 스크롤
const visibleBoards = ref([])
let page = 1
const pageSize = 8

// 일부만 렌더링할 함수
const setVisibleData = () => {
    const start = (page - 1) * pageSize
    const end = page * pageSize
    visibleBoards.value = [...visibleBoards.value, ...store.boards.slice(start, end)]
}

const loadMoreData = () => {
    page++
    setVisibleData()
}

const divDom = document.querySelector('div')
window.onscroll = () => {
    if(divDom.scrollHeight - (window.innerHeight + window.scrollY) < 10)
        loadMoreData()
    // console.log(window.innerHeight)
    // console.log(window.scrollY)
    // console.log("스크롤 heigt :  "+divDom.scrollHeight)
}

</script>

<style scoped>
* {
    font-family: 'Nunito', sans-serif;
    font-family: 'Noto Sans KR', sans-serif;
}
.item-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center ;
    width: 100%;
    height: 10%;
}
.boards-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.board-header {
    margin: 2rem 0;
    padding: 0 4rem;
    display: flex;
    justify-content: space-between;
    width: 100%;
}

.board-left-menu {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.board-title {
    font-size: 1.5rem;
}
.sort-btn {
    margin-right: 5px;
    background-color: white;
    border: 1px solid rgb(198, 198, 198);
    border-radius: 10px;
}
.sort-btn:hover {
    background-color: cadetblue;
    color: white;
}

.board-right-menu {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}
.write-box {
    margin-bottom: 1rem;
    width: 2rem;
}
.board-right-menu input {
    border: none;
    border-bottom: 2px solid rgb(130, 130, 130);
}

.board-right-menu label {
    width: 1rem;
}


.board-right-menu button {
    border: none;
    padding: 0;
    width: 1.5rem;
    background-color: white;
}

.board-right-menu img {
    width: 100%;
}
</style>