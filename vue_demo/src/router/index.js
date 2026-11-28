

import { createRouter, createWebHistory } from 'vue-router';
import StudentLogin from '../views/StudentLogin.vue';
import HelloWorld from '../views/HelloWorld.vue';
import FacultyView from '../views/FacultyView.vue' 


const routes = [
  {
    path: '/',
    name: 'Login',
    component: StudentLogin
  },
  {
    path: '/helloworld',
    name: 'HelloWorld',
    component: HelloWorld
  },
  {
  path: '/faculty',
  name: 'Faculty',
  component: FacultyView
  }
];
const router = createRouter({
  history: createWebHistory(),
  routes
});
export default router;
