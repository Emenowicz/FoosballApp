import {USER_ERROR, USER_REQUEST, USER_SUCCESS} from "../actions/user";
import {AUTH_LOGOUT} from "../actions/authentication";
import axios from "axios";

const state = {status: '', profile: {}}

const getters = {
    getProfile: state => state.profile,
    isProfileLoaded: state => !!state.profile.name
}

const actions = {
    [USER_REQUEST]: ({commit, dispatch}) => {
        commit(USER_REQUEST)
        axios.get('http://localhost:8088/users/me')
            .then(resp => {
                commit(USER_SUCCESS, resp.data)
            }).catch(resp => {
            commit(USER_ERROR)
            dispatch(AUTH_LOGOUT)
        })
    }
}

const mutations = {
    [USER_REQUEST]: (state) => {
        state.status = 'loading'
    },
    [USER_SUCCESS]: (state, resp) => {
        state.status = 'success'
        state.profile = resp
    },
    [USER_ERROR]: (state) => {
        state.status = 'error'
    },
    [AUTH_LOGOUT]: (state) => {
        state.profile = {}
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}