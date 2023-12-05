import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from '@/router'

const BoardAPI = `http://localhost:8080/api-board`;
export const useBoardStore = defineStore("board", () => {
  const boards = ref([]);

  // 게시글 list 세팅
  const setBoard = () => {
    axios.get(`${BoardAPI}/boards/`)
      .then((res) => {
        boards.value = res.data
      })
  }
  // 게시글 정렬
  const setSortedBoard = (sortedBoards) => {
    boards.value = sortedBoards
  }

  // 게시글 등록
  const regist = (file, userId, content, hashTags) => {

    const f = new FormData
    f.append('file', file)
    f.append('userId', userId)
    f.append('content', content)
    f.append("hashTags", hashTags)

    axios.post(`${BoardAPI}/regist`, f, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      transformRequest: [(data, headers) => data],
    })
      .then((res) => {
        alert('게시글 등록 완료')
        router.push({ name: 'list', params: {tag: 'all'} })
      })
      .catch((error) => {
        console.error('에러', error)
      })
  }
  // 게시글 상세
  const board = ref([])
  const getBoard = (id, loginUserId) => {
    console.log(loginUserId)
    axios.get(`${BoardAPI}/detail/${ id }?userId=${ loginUserId }`)
      .then((res) => {
        board.value = res.data
      })
  }

  // 게시글 수정
  const updateBoard = (updateboard) => {
    axios.put(`${BoardAPI}/update`, updateboard)
      .then((res) => {
        router.push({ name: 'detail', params: { id: board.value.boardId } })
      })
  }

  //해시태그로 게시글찾기
  const search = (tag) => {
    if (tag == 'all') {
      setBoard();
      return;
    }
    axios.get(`${BoardAPI}/search?tag=${tag}`)
      .then((res) => {
        boards.value = res.data;
    });
  };

  return { boards, setBoard, regist, board, getBoard, updateBoard, search, setSortedBoard };
});
