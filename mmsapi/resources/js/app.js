require('./bootstrap');

// Tell Vue to import the plugins
import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuetify from 'vuetify/lib'
import vuetify from '@/plugins/vuetify'
import VueCookie from 'vue-cookie'
import axiosVue from 'axios-vue'

// Tell Vue to use the plugins
Vue.use(VueCookie);
Vue.use(VueRouter)
Vue.use(Vuetify)
 
Vue.use(axiosVue)