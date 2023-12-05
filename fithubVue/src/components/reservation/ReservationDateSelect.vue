<template>
  <div class="container">
    <label for="select-date" class="select-label">날짜 선택</label>
    <Datepicker v-model="pickedDate" :locale='locale' :format="formatDate" :enable-time-picker="false"
      :disabledDates="{ predicate: disable }" class="custom-css" id="select-date"/>
    <button @click="testF" class="lesson-view-bnt">수업 조회</button>

  </div>
</template>

<script setup>
import Datepicker from 'vue3-datepicker'
import { ref } from 'vue';
import { add } from 'date-fns'
import ko from 'date-fns/locale/ko'
import { useReservationStore } from '@/stores/reservation'
import { useUserStore } from '@/stores/user';
const userStore=useUserStore();
const store = useReservationStore();


const locale = ko;

const today = new Date()
const yesterday = new Date(today)
yesterday.setDate(today.getDate() - 1)

const pickedDate = ref(new Date())
const disable = (date) => {
  return date < new Date(yesterday) || date > add(new Date(), { days: 7 })
}

const formatDate = () => {
  const month = pickedDate.value.getMonth() + 1
  const day = pickedDate.value.getDate()

  if (month || day < 10) {
    const zeroDay = ('00' + day).slice(-2)
    const zeroMonth = ('00' + month).slice(-2)
    return `${zeroMonth}-${zeroDay}`
  } else {
    return `${month}-${day}`
  }
}

const testF = () => {
  const a = {
    placeName: store.place,
    date1: formatDate(),
    userId : userStore.loginUserId?userStore.loginUserId:-1
  }
  store.getLessons(a);
}

</script>

<style scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.select-label {
  padding: 3px 10px;
  margin-bottom: 1rem;
  border: solid 1px rgb(198, 198, 198);
  border-radius: 5px;
  background-color: white;
  cursor: pointer;
}

.select-label:hover {
  color: white;
  border: solid 1px cadetblue;
  background-color: cadetblue;
}
.lesson-view-bnt {
  margin-top: 2rem;
  font-size: 1.3rem;
  width: 50%;
  border: 2px solid cadetblue;
  border-radius: 10px;
  color: white;
  background-color: cadetblue;
  box-shadow: 0px 5px 15px 1px rgb(104, 134, 112);
}

.lesson-view-bnt:hover {
  color: black;
  background-color: white;
}

</style>