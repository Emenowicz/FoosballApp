import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import Register from './views/Register.vue'
import Login from './views/Login.vue'
import MyAccount from './views/MyAccount.vue'
import NewTournament from './views/NewTournament.vue'
import UserMainPage from './views/UserMainPage.vue'
import TournamentPage from './views/TournamentPage'
import AllTournaments from './views/AllTournaments'
import Statistics from './views/Statistics'

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
            name: 'UserMainPage',
            component: UserMainPage,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/myaccount',
            name: 'MyAccount',
            component: MyAccount,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/new-tournament',
            name: 'NewTournament',
            component: NewTournament,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/tournament/:id',
            name: 'TournamentPage',
            component: TournamentPage,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/all-tournaments',
            name: 'AllTournaments',
            component: AllTournaments,
            beforeEnter: ifAuthenticated
        },
        {
            path: '/my-statistics',
            name: 'Statistics',
            component: Statistics,
            beforeEnter: ifAuthenticated
        }
    ]
})
