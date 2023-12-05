<template>
    <div class="boards-container">
        <div class="board-header">
            <div>
                <h2>MY FEED</h2>
            </div>
        </div>
        <div class="item-container" id="item-container">
            <BoardListItem v-for="board in store.myBoards" :key="board.boardId" :board="board" />
        </div>
    </div>
</template>

<script setup>
import { useMyPageStore } from '@/stores/mypage'
import { storeToRefs } from 'pinia';
import { ref, onMounted } from 'vue'
import BoardListItem from '../board/BoardListItem.vue';
const store = useMyPageStore()

onMounted(() => {
    store.getMyBoards(sessionStorage.getItem('loginUserId'))
})



</script>

<style  scoped>
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
    align-items: center;
}
.board-header {
    margin: 2rem 0;
    padding: 0 4rem;
    display: flex;
    justify-content: space-between;
    width: 100%;
}

</style>