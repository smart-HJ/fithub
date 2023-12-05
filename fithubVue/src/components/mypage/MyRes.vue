<template>
    <div>
        <VCalendar :attributes="attributes" expanded @dayclick="handleDayClick">
        </VCalendar>
    </div>
    <!-- 보라: 역삼점 / 핑크: 송파점/ 채워진거 : 이미 수강완료 /텅빈동그라미: 수강예정(예약취소가능) -->
    <div v-if="lessons.length > 0">
        <div v-for="lesson in lessons" :key="lesson.lessonId" class="lesson-box">
            <div class="item-container">
                <div class="left-item-container">
                    <div class="seletd-box">
                        <span v-if="lesson.placeId == 1">역삼점</span>
                        <span v-else>송파점</span>
                        <span> {{ lesson.date }} {{ lesson.time }}</span>
    
                    </div>
                </div>
                <div class="right-item-container">
                    <div v-if="((parseInt(lesson.date.substr(0, 2)) > today.getMonth() + 1) || (parseInt(lesson.date.substr(0, 2)) == today.getMonth() + 1 && parseInt(lesson.date.substr(3, 5)) > today.getDate()) || (parseInt(lesson.date.substr(0, 2)) == today.getMonth() + 1 && parseInt(lesson.date.substr(3, 5)) == today.getDate() && parseInt(lesson.time.substr(0, 2)) > today.getHours()))"
                        class="res-cancel-btn" @click="deleteRes(lesson.lessonId)">예약취소</div>
                    <div v-else>수강완료</div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script setup>
import { ref, onMounted, onUpdated, watch } from 'vue';
import axios from 'axios';
import { useMyPageStore } from '@/stores/mypage'
import { useRouter } from 'vue-router';

const router = useRouter()

const store = useMyPageStore();

const lessons = ref([])
const attributes = ref([]);
const today = new Date();

onMounted(() => {
    store.getMyLessons(sessionStorage.getItem('loginUserId'));
    console.log(store.mylessons)
    
});

watch(() => store.oneDayLessons, (newOneDayLessons) => {
  lessons.value = newOneDayLessons;
});

// 친구
watch(() => store.mylessons, (newMylessons) => {
  if (newMylessons) {
    attributes.value = []; // attributes 초기화
    newMylessons.forEach(function (lesson) {
      // 새로운 attributes 구성 로직
      const date = new Date(2023, lesson.date.substr(0, 2) - 1, lesson.date.substr(3, 5));
      let color = '';
      let fillMode = '';
      if (lesson.placeId == 1) {
        color = 'purple';
        if (
          (parseInt(lesson.date.substr(0, 2)) < today.getMonth() + 1) ||
          (parseInt(lesson.date.substr(0, 2)) == today.getMonth() + 1 && parseInt(lesson.date.substr(3, 5)) < today.getDate()) ||
          (parseInt(lesson.date.substr(0, 2)) == today.getMonth() + 1 && parseInt(lesson.date.substr(3, 5)) == today.getDate() && parseInt(lesson.time.substr(0, 2)) <= today.getHours())
        ) {
          fillMode = 'solid';
        } else {
          fillMode = 'outline';
        }
      } else {
        color = 'pink';
        if (
          (parseInt(lesson.date.substr(0, 2)) < today.getMonth() + 1) ||
          (parseInt(lesson.date.substr(0, 2)) == today.getMonth() + 1 && parseInt(lesson.date.substr(3, 5)) < today.getDate()) ||
          (parseInt(lesson.date.substr(0, 2)) == today.getMonth() + 1 && parseInt(lesson.date.substr(3, 5)) == today.getDate() && parseInt(lesson.time.substr(0, 2)) <= today.getHours())
        ) {
          fillMode = 'solid';
        } else {
          fillMode = 'outline';
        }
      }
      attributes.value.push({
        highlight: {
          color: color,
          fillMode: fillMode,
        },
        dates: [date],
      });
    });
  }
});




const handleDayClick = (day) => {
    store.getOneDayLessons(day.date.getMonth()+1 + '-' + day.date.getDate(), sessionStorage.getItem('loginUserId'))
    console.log(store.oneDayLessons)
};

// 예약 취소
const deleteRes = (lessonId) => {
    if(confirm('취소하시겠습니까?'))
        axios.delete(`http://localhost:8080/api-reservation/delete?userId=${sessionStorage.getItem('loginUserId')}&lessonId=${lessonId}`)
            .then((res)=>{
                store.getMyLessons(sessionStorage.getItem('loginUserId'));
                lessons.value = []
             })
}


</script>

<style scoped>
div {
    width: 70%;
}
.lesson-box{
  width: 100%;
}
.item-container {
    display: flex;
    width: 100%;
    height: 4rem;
    padding: 1rem;
    margin-bottom: 20px;
    margin-top: 40px;
    border-radius: 20px;
    background-color: #f5f5f5;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.left-item-container {
    display: flex;
    flex-direction: column;
    width: 60%;
    font-size: 17px;

}

.seletd-box {
    display: flex;
    justify-content: space-evenly;
    width: 100%;
    height: 100%;
}

.seletd-box span {
  display: flex;
  align-items: center;
}

.right-item-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40%;
}
.right-item-container div {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
}

.res-cancel-btn {
    text-align: center;
    padding: 5px 10px;
    border: 2px solid rgb(231, 199, 214);
    background-color: rgb(231, 199, 214);
    border-radius: 10px;
    cursor: pointer;
}

.res-cancel-btn:hover {
    border: 2px solid rgb(231, 199, 214);
    background-color: white;
}</style>
