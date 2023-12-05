import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from '@/router'

const MyPageAPI = `http://localhost:8080/api-mypage`;

export const useMyPageStore = defineStore("mypage", () => {
  const mylessons = ref([]);
  const oneDayLessons = ref([]);

  const getOneDayLessons = function (day, userId) {

    axios.get(`${MyPageAPI}/day?date1=${day}&userId=${userId}`).then((res) => {
      console.log(res.data)
      oneDayLessons.value = res.data;
    });
  };

  const getMyLessons = function (userId) {
    axios.get(`${MyPageAPI}/lessons?userId=${userId}`).then((res) => {
      mylessons.value = res.data;
    });
  };

  const myBoards = ref([])
  const getMyBoards = (userId) => {
    axios.get(`${MyPageAPI}/board?userId=${userId}`)
      .then((res) => {
        myBoards.value = res.data
      })
  }

  const user = ref('')
  const getUser = (userId) => {
    axios.get(`${MyPageAPI}/user?userId=${userId}`)
      .then((res) => {
        user.value = res.data
      })
  }

  const updateInfo = (file, userId, id) => {
    const f = new FormData
    f.append('file', file)
    f.append('userId', userId)
    f.append('id', id)

    axios.put(`${MyPageAPI}/update`, f, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      transformRequest: [(data, headers) => data],
    })
      .then((res) => {
        alert('정보가 수정되었습니다.')
      })
  }

  const changePassword = (password, userId) => {
    console.log(password)
    console.log(userId)
    axios.put(`${MyPageAPI}/changePassword`, {password, userId}).then((res) => {
      console.log(res)
    })
  }

  return { getMyLessons, mylessons, oneDayLessons, getOneDayLessons, myBoards, getMyBoards, user, getUser, updateInfo, changePassword};
});
