import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import Register from './views/Register.vue'
import Login from './views/Login.vue'
import MyAccount from './views/MyAccount.vue'
import MainPage from './views/MainPage.vue'

Vue.use(Router)

const ifNotAuthenticated = (to, from, next) => {
    if (!store.getters.isAuthenticated) {
        next()
        return
    }
    next('/')
}

const ifAuthenticated = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next()
        return
    }
    next('/login')
}


export default new Router({
    routes: [
        {
            path: '/login',
            name: 'Login',
            component: Login,
            beforeEnter: ifNotAuthenticated
        },
        {
            path: '/register',
            name: 'Register',
            component: Register,
            beforeEnter: ifNotAuthenticated
        },
        {
            path: '/',
            name: 'MainPage',
            component: MainPage,
        },
        {
            path: '/myaccount',
            name: 'MyAccount',
            component: MyAccount,
            beforeEnter: ifAuthenticated
        }
    ]
})
