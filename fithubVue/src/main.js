import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import "v-calendar/dist/style.css";
import VCalendar from "v-calendar";



import App from './App.vue'
import router from './router'
import axios from 'axios'
// axios.defaults.withCredentials = true;

const app = createApp(App)

app.use(VCalendar, {});
app.use(createPinia())
app.use(router)

app.mount('#app')
