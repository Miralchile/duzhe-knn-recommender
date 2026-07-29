import Vue from 'vue'
import App from './App'
import axios from "axios";
import VueAxios from "vue-axios";
import uView from '@/uni_modules/uview-ui';
import store from './store/index.js'
Vue.use(VueAxios, axios);
Vue.use(uView)

Vue.config.productionTip = false;

App.mpType = 'app'
Vue.prototype.$store = store

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	store,
	...App
})
app.$mount()


