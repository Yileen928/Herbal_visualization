import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import jQuery from 'jquery';
import Vue3Lottie from 'vue3-lottie';
import './global-tooltip-fix.css'; // 导入全局tooltip修复样式

window.$ = window.jQuery = jQuery;

const app = createApp(App);
app.use(router);
app.component('Vue3Lottie', Vue3Lottie); // 全局注册组件
app.mount('#app');
