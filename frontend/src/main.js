import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import jQuery from 'jquery';

window.$ = window.jQuery = jQuery;

const app = createApp(App);
app.use(router);
app.mount('#app');
