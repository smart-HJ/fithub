<template>
  <div class="container">
    <button class="select-btn" @click="modalOpen">지점 선택</button>
    <div class="placeName">{{ store.place }}</div>
  </div>

  <div class="modal-wrap" v-show="modalCheck" @click="modalOpen">
    <div class="modal-container" @click.stop>
      <div id="map"></div>
      <div class="modal-btn">
        <button @click="modalOpen">닫기</button>
        <button @click="modalOpen">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUpdated, ref, toRaw } from 'vue';
import { useReservationStore } from '@/stores/reservation'
import { useUserStore } from '@/stores/user';
const userStore=useUserStore();
const store = useReservationStore();

const modalCheck = ref(false);

const modalOpen = function () {
  modalCheck.value = !modalCheck.value
  console.log(store.place)
}

let map = null;
const initMap = function () {
  var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = {
      center: new kakao.maps.LatLng(37.50142980571555, 127.0382306747513), // 지도의 중심좌표
      level: 2 // 지도의 확대 레벨
    };

  var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

  // 마커를 표시할 위치와 title 객체 배열입니다 
  var positions = [
    {
      title: '역삼점',
      latlng: new kakao.maps.LatLng(37.50105571141196, 127.0387733218092)
    },
    {
      title: '송파점',
      latlng: new kakao.maps.LatLng(37.4808488433061, 127.12123502112762)
    },


  ];

  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

  for (var i = 0; i < positions.length; i++) {

    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35);

    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
      title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage // 마커 이미지 
    });
    // 마커에 표시할 인포윈도우를 생성합니다 
    var infowindow = new kakao.maps.InfoWindow({
      content: positions[i].title // 인포윈도우에 표시할 내용
    });

    // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
    // 이벤트 리스너로는 클로저를 만들어 등록합니다 
    // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
    kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'click', makeClickListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
  }
  // 지점 클릭할 때
  function makeClickListener(map, marker, infowindow) {
    return function () {
      store.place = marker.Gb
      const a = {
        placeName: store.place,
        date1: store.dateStr,
        userId : userStore.loginUserId?userStore.loginUserId:-1
      }
      store.getLessons(a);
      modalOpen();
    };
  }
  // 지점에 마우스 올릴 때
  function makeOverListener(map, marker, infowindow) {
    return function () {
      infowindow.open(map, marker);
    };
  }

  // 인포윈도우를 닫는 클로저를 만드는 함수입니다 
  function makeOutListener(infowindow) {
    return function () {
      infowindow.close();
    };
  }

  const zoomControl = new kakao.maps.ZoomControl();
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
};

onUpdated(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement('script'); // autoload=false 스크립트를 동적으로 로드하기 위해서 사용
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_API_KEY
      }`;
    script.addEventListener('load', () => {
      kakao.maps.load(initMap);
    }); //헤드태그에 추가
    document.head.appendChild(script);
  }
});

const myMarkerPosition = ref([
  [33.450701, 126.570667],
])

const markers = ref([]);


</script>

<style scoped>
* {
  font-family: 'Noto Sans KR', sans-serif;
}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.select-btn {
  padding: 3px 10px;
  margin-bottom: 5px;
  border: solid 1px rgb(198, 198, 198);
  border-radius: 5px;
  background-color: white;
}

.select-btn:hover {
  color: white;
  border: solid 1px cadetblue;
  background-color: cadetblue;
}


.placeName {

  font-size: 20px;
}



#map {
  width: 500px;
  height: 400px;
}


/* dimmed */
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}

/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
</style>
