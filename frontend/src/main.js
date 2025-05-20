import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import jQuery from 'jquery';
import axios from 'axios';

window.$ = window.jQuery = jQuery;

// 配置 axios 默认值
axios.defaults.baseURL = '/api';  // 修改为相对路径，通过nginx代理
axios.defaults.timeout = 5000;  // 设置超时时间

const app = createApp(App);
app.use(router);
app.mount('#app');
