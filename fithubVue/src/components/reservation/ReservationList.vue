<template>
  <div class="container">
    <div class="left-container">
      <div class="text-box">RESERVATION</div>
      <div class="place-container">
        <ReservationPlaceSelect />
      </div>
      <div class="date-container">
        <ReservationDateSelect />
      </div>
    </div>
    <div class="right-container">
      <div class="bookingList-container">
        <ReservationListItem v-for="lesson in store.lessons" :key="lesson.lessonId" :lesson="lesson" />
        <p v-if="!store.lessons">수업이 없습니다. </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import ReservationDateSelect from './ReservationDateSelect.vue';
import ReservationPlaceSelect from './ReservationPlaceSelect.vue';
import ReservationListItem from './ReservationListItem.vue'
import { useReservationStore } from '@/stores/reservation';
import { useUserStore } from '@/stores/user';

import { onMounted } from 'vue'

const userStore=useUserStore();
const store = useReservationStore();
onMounted(() => {
  console.log(userStore.loginUserId)
  const a = {
    placeName: store.place,
    date1: store.dateStr,
    userId : userStore.loginUserId?userStore.loginUserId:-1
  }
  console.log(a.date1)
  store.getLessons(a);
});

</script>

<style  scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
}
.container {
  display: flex;
  padding-top: 10%;
  width: 80%;
}
.left-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 50%;
  height: 60vh;
  border: 2px solid rgb(198, 198, 198);
  border-radius: 20px 0 0 20px;
  box-shadow: 0 0 5px 5px rgb(198, 198, 198);

}
.text-box {
  margin-bottom: 30px;
  text-align: center;
  font-size: 2rem;
  margin: 0 auto;
  background: linear-gradient(to top, cadetblue, 10%, transparent 10%);
}

.right-container {
  padding: 3%;
  width: 50%;
  height: 60vh;
  border: 2px solid rgb(198, 198, 198);
  border-radius: 0 20px 20px 0;
  box-shadow: 0 0 5px 5px rgb(198, 198, 198);
  
}
.place-container {
  margin: 2rem 0 2rem;
}
.date-container {
  margin-bottom: 2rem;
  width: 100%;
}
.bookingList-container{
  width: 100%;
}
</style>