import { ref, computed, watchEffect  } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import router from '@/router'
import Datepicker from 'vue3-datepicker'

const ReservationAPI = `http://localhost:8080/api-reservation`;
export const useReservationStore = defineStore("reservation", () => {
  const lessons = ref([]);
  const place = ref('역삼점');


  const pickedDate = ref(new Date());

  const dateStr = ref('')
  const formatDate = () => {
    const month = pickedDate.value.getMonth() + 1
    const day = pickedDate.value.getDate()

    if (month || day < 10) {
      const zeroDay = ('00' + day).slice(-2)
      const zeroMonth = ('00' + month).slice(-2)
      dateStr.value = `${zeroMonth}-${zeroDay}`
    } else {
      dateStr.value = `${month}-${day}`
    }
  }

  const getLessons = function (a) {
    axios.get(`${ReservationAPI}/lesson?date1=${a.date1}&placeName=${a.placeName}&userId=${a.userId}`)
      .then((res) => {
        lessons.value=res.data
        console.log(res)
      })
  }
  watchEffect(() => {
    formatDate()
  });



  return { lessons, getLessons, pickedDate, place, formatDate, dateStr};
});
