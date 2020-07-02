import Vue from 'vue';
import {store} from '../store/index';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Login from "../views/Login";


Vue.use(VueRouter)

let router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home,
            meta: {
                title: 'Home',
                requiresAuth: true,
            }
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                title: 'Login',
                requiresAuth: false,
                layout: 'no-header'
            }
        },
        {
            path: '/register',
            name: 'Register',
            component: () => import("../views/Register"),
            meta: {
                title: 'Register',
                requiresAuth: false,
                layout: 'no-header'
            }
        },
        {
            path: '/profile',
            name: 'Profile',
            component: () => import("../views/Profile"),
            meta: {
                title: 'My Profile',
                requiresAuth: true
            }
        }
    ]
})

router.beforeEach((to, from, next) => {
    document.title = to.meta.title;
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.isAuthenticated) {
            next({path: '/login', params: {nextUrl: to.fullPath}})
        }
        else
            next();
    }
    else
        next();
});

export default router;
