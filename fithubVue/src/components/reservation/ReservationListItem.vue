<template>
    <div class="item-container">
        <!-- <p>수업번호(확인용) : {{lesson.lessonId}}</p> -->
        <div class="left-item-container">
            <div class="seletd-box">
                <p>수강일 : {{lesson.date}}</p>
                <p>시간 : {{lesson.time}}</p>
            </div>
            <div class="capacity-box">
                <p>인원 : {{`${lesson.resCnt} / ${lesson.capacity}`}}</p>
            </div>
        </div>
        <div class="right-item-container">
            <div v-if="lesson.resCnt==lesson.capacity">예약마감</div>
            <div v-else>
                <div v-if="lesson.resFlag" @click="deleteRes" class="res-cancel-btn">예약취소</div>
                 <div v-else @click="KGpay" class="res-btn">예약하기</div>
             </div>
        </div>
        
    </div>
</template>

<script setup>
import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useReservationStore } from '@/stores/reservation'
import { useRoute, useRouter } from 'vue-router'
import { useMyPageStore } from '@/stores/mypage';

const router = useRouter()
const userStore = useUserStore()
const store = useReservationStore()
const myPageStore=useMyPageStore()

const props=defineProps({
    lesson: Object,
    required: true,
})

const deleteRes=function(){
    if(confirm('취소하시겠습니까?'))
        axios.delete(`http://localhost:8080/api-reservation/delete?userId=${userId}&lessonId=${lessonId}`)
            .then((res)=>{
                props.lesson.resCnt--
                props.lesson.resFlag = !props.lesson.resFlag
                myPageStore.getMyLessons(sessionStorage.getItem('loginUserId'));
             })
}


// 결제 여기서 부터
const place = props.lesson.placeId == 1 ? '역삼점' : '송파점'


const userId = userStore.loginUserId
const lessonId = props.lesson.lessonId

const VITE_KGPAY_API_KEY = import.meta.env.VITE_KGPAY_API_KEY;
const KGpay = () => {
    const token = sessionStorage.getItem('access-token');
    if(token==null){
        alert('로그인이 필요합니다.')
        router.push({ name: 'login' })
    }
    else {
        IMP.init(VITE_KGPAY_API_KEY)
        IMP.request_pay({
            pg: 'html5_inicis',
            pay_method: "card",
            merchant_uid: userStore.loginUserId + '_' +new Date().getTime(),
            name: 'fithub ' + place,
            amount: 100,
            buyer_name: userStore.loginId,
        }, rsp =>{
            // 엄청난 .. 검증 과정이 있어야한다..
            // back에서도 검증을 해줘야한다.
            if(rsp.success){
                axios.post(`http://localhost:8080/api-reservation/resPay`, {userId, lessonId})
                    .then((res)=>{
                        props.lesson.resCnt++
                        props.lesson.resFlag = !props.lesson.resFlag
                        myPageStore.getMyLessons(sessionStorage.getItem('loginUserId'));
                    })
                alert('결제되었습니다.')
            } else {
                console.log('결제 실패')
            }
        })
    }
}
///////////////여기까지다
</script>

<style scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
}
.item-container {
    display: flex;
    width: 100%;
    padding: 1rem;
    margin-bottom: 20px;
    border-radius: 20px;
    background-color: #f5f5f5; /* 배경색 설정 */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
}

.left-item-container {
    display: flex;
    flex-direction: column;
    width: 60%;
    font-size: 17px;

}
.seletd-box {
    display: flex;
    justify-content: space-around;
}
.capacity-box {
    text-align: center;
}
.right-item-container {
    display: grid;
    place-items: center;
    width: 40%;
}

.res-btn {
    font-size: 1rem;
    padding: 8px 15px;
    border: 2px solid cadetblue;
    background-color: white;
    border-radius: 10px;
    cursor: pointer;
}

.res-cancel-btn {
    font-size: 1rem;
    padding: 8px 15px;
    border: 2px solid  cadetblue;
    background-color:   cadetblue;
    border-radius: 10px;
    cursor: pointer;
}

.res-btn:hover {
    border: 1px solid cadetblue;
    background-color: cadetblue;
}

.res-cancel-btn:hover {
    border: 2px solid cadetblue;
    background-color: white;
}


</style>