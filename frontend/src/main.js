import '@babel/polyfill'
import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from "axios"
import Vuelidate from 'vuelidate'
import {USER_REQUEST} from "./store/actions/user";
import moment from "moment";

Vue.config.productionTip = false
Vue.use(Vuelidate)
Vue.prototype.moment = moment
moment.locale('pl')

const token = localStorage.getItem('user-token')
if (token) {
    axios.defaults.headers.common = {'Authorization': "bearer" + token}
    store.dispatch(USER_REQUEST)
}

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
