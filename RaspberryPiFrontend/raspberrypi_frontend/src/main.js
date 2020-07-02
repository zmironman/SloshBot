//region Imports
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import {store} from './store'
import './registerServiceWorker'
import axios from 'axios';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import VeeValidate from 'vee-validate';
import {library} from '@fortawesome/fontawesome-svg-core';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import {
    faHome,
    faUser,
    faUserPlus,
    faSignInAlt,
    faSignOutAlt
} from '@fortawesome/free-solid-svg-icons';
import Default from '../src/components/layouts/Default'
import NoHeader from './components/layouts/NoHeader'
//endregion

//region Setting Defaults and adding components
Vue.use(VeeValidate);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component('default-layout', Default);
Vue.component('no-header-layout', NoHeader);
library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt);

const APISERVER = process.env.VUE_APP_API_SERVER;
axios.defaults.baseURL = `${APISERVER}`;

Vue.config.productionTip = false
//endregion

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
