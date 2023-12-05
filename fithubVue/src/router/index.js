import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserView from '@/views/UserView.vue'
import UserRegist from '@/components/user/UserRegist.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import BoardView from '@/views/BoardView.vue'
import BoardList from '@/components/board/BoardList.vue'
import BoardRegist from '@/components/board/BoardRegist.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'
import BoardUpdate from '@/components/board/BoardUpdate.vue'
import VideoView from '@/views/VideoView.vue'
import VideoList from '@/components/video/VideoList.vue'
import ReservationView from '@/views/ReservationView.vue'
import ReservationList from '@/components/reservation/ReservationList.vue'
import MyPageView from '@/views/MyPageView.vue'
import MyRes from '@/components/mypage/MyRes.vue'
import MyFeed from '@/components/mypage/MyFeed.vue'
import MyInfo from '@/components/mypage/MyInfo.vue'



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/user',
      name: 'user',
      component: UserView,
      children:[
        {
          path: 'regist',
          name: 'regist',
          component: UserRegist,
        },
        {
          path: 'login',
          name: 'login',
          component: UserLogin,
        },
    
      ]
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView,
      children:[
        {
          path: 'list/:tag',
          name: 'list',
          component: BoardList,
        },
        {
          path: 'write',
          name: 'write',
          component: BoardRegist,
        },
        {
          path: ':id',
          name: 'detail',
          component: BoardDetail,
        },
        {
          path: 'update',
          name: 'update',
          component: BoardUpdate,
        },
      ]
    },
    {
      path: '/video',
      name: 'video',
      component: VideoView,
      children: [
        {
          path: '',
          name: 'vidoeList',
          component: VideoList,
        }
      ]
    },
    {
      path: '/reservation',
      name: 'reservation',
      component: ReservationView,
      children: [
        {
          path: '',
          name: 'reservationList',
          component: ReservationList,
        }
      ]
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView,
      children: [
        {
          path: "",
          name: "myres",
          component: MyRes,
        },
        {
          path: "myfeed",
          name: "myfeed",
          component: MyFeed,
        },
        {
          path: "myInfo",
          name: "myInfo",
          component: MyInfo,
        },
      ],
    },



  ]
})

export default router
