import ApiConstants from "../../libs/ApiConstants";
import axios from 'axios'
import {AUTH_ERROR, AUTH_LOGOUT, AUTH_REQUEST, AUTH_SUCCESS} from "../actions/authentication";
import {USER_REQUEST} from "../actions/user";

const state = {
    token: localStorage.getItem('user-token') || '',
    status: '',
}
const mutations = {
    [AUTH_REQUEST]: (state) => {
        state.status = 'loading'
    },
    [AUTH_SUCCESS]: (state, token) => {
        state.status = 'success'
        state.token = token
    },
    [AUTH_ERROR]: (state) => {
        state.status = 'error'
    },
    [AUTH_LOGOUT]: (state) => {
        state.token = ''
    }
}
const actions = {
    [AUTH_REQUEST]: ({commit, dispatch}, user) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_REQUEST)
            const params = new URLSearchParams();
            params.append('grant_type', user.grant_type)
            params.append('username', user.username)
            params.append('password', user.password)
            axios(
                {
                    url: ApiConstants.LOGIN_ENDPOINT,
                    data: params,
                    auth:{username: 'fusball', password:'secret'},
                    headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
                    method: 'POST'
                })
                .then(resp => {
                    debugger;
                    const token = resp.data.access_token
                    localStorage.setItem('user-token', token)
                    commit(AUTH_SUCCESS, token)
                    dispatch(USER_REQUEST)
                    resolve(resp)
                })
                .catch(err => {
                    commit(AUTH_ERROR, err)
                    localStorage.removeItem('user-token')
                    reject(err)
                })
        })
    },
    [AUTH_LOGOUT]: ({commit, dispatch}) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_LOGOUT)
            localStorage.removeItem('user-token')
            resolve()
        })
    }
}
const getters = {
    isAuthenticated: state => !!state.token,
    authStatus: state => state.status,
}

export default {
    state,
    mutations,
    actions,
    getters
}