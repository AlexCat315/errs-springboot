import { createApp } from 'vue'

import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "../src/router/router";
import axios from "axios";
const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.mount('#app')
const baseDevUrl = "http://127.0.0.1:12345"
const baseProdUrl = "https://www.alexcat.it.com"
axios.defaults.baseURL = baseDevUrl