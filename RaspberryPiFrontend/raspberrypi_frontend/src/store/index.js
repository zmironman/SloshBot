import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'

Vue.use(Vuex)

const vuexSessionStorage = new VuexPersistence({
    key: "vuex",
    storage: window.sessionStorage,
    reducer: state => ({
        Superuser: state.Superuser,
        Admin: state.Admin,
        Moderator: state.Moderator,
        user: state.user,
        authenticated: state.authenticated
    })
})

export const store = new Vuex.Store({
    state: {
        Superuser: false,
        Admin: false,
        Moderator: false,
        user: {},
        authenticated: false
    },
    mutations: {
        setAccess(state, n) {
            if (n == 0) {
                state.Superuser = false;
                state.Admin = false;
                state.Moderator = false;
                state.authenticated = false;
            } else {
                state.authenticated = true;
                if(n > 2)
                    state.Moderator = true;
                if(n > 3)
                    state.Admin = true;
                if(n > 4)
                    state.Superuser = true;
            }
        },
        setUser(state, o){
            state.user = o;
        },
        logout(state){
            state.Superuser = false;
            state.Admin = false;
            state.Moderator = false;
            state.authenticated = false;
            state.user = {};
            localStorage.removeItem('user');
        }
    },
    getters: {
        isAuthenticated: state => {
            return state.authenticated
        },
        isAdmin: state => {
            return state.Admin
        },
        isModerator: state => {
            return state.Moderator
        }
    },
    actions: {},
    modules: {},
    plugins: [vuexSessionStorage.plugin]
})
