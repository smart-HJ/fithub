import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from "@/router";

const UserAPI = `http://localhost:8080/api-user`;
export const useUserStore = defineStore("user", () => {
  const users = ref([]);
  const loginUserId = ref(sessionStorage.getItem("loginUserId") || -1);
  const loginId = ref(sessionStorage.getItem("loginId" || ""));
  const setUsers = function () {
    axios.get(`${UserAPI}/user`).then((res) => {
      users.value = res.data;
    });
  };

  const regist = (file, id, name, password, email, phoneNum) => {
    const f = new FormData
    f.append('file', file)
    f.append('id', id)
    f.append('name', name)
    f.append('password', password)
    f.append('email', email)
    f.append('phoneNum', phoneNum)

    axios.post(`${UserAPI}/regist`, f, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      transformRequest: [(data, headers ) => data],
    })
      .then((res) => {
      router.push({ name: "login" });
    })
      .catch((error) => {
        console.log('에러: ', error)
      })
  };

  const login = function (user) {
    console.log(user)
    axios
      .post(`${UserAPI}/login`, user)
      .then((res) => {
        console.log(res.data)
        sessionStorage.setItem("access-token", res.data["access-token"]);
        const token = res.data["access-token"].split(".");
        let id = token[1];
        id = atob(id);
        id = JSON.parse(id);
        loginId.value = id["id"];
        loginUserId.value = id["userId"];

        sessionStorage.setItem("loginUserId", id["userId"])
        sessionStorage.setItem("loginId", id["id"])

        router.push({ name: "home" });
      })
      .catch(() => {
        alert("로그인 실패");
        router.push({ name: "login" });
      });
  };

  return { setUsers, users, regist, login, loginUserId, loginId };
});
