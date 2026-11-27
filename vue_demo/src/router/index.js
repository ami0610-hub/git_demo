// URLと画面コンポーネントの対応を定義する。
// main.jsでアプリに登録され、StudentLogin.vueとHelloWorld.vueへの遷移を管理する。
import { createRouter, createWebHistory } from 'vue-router';
import StudentLogin from '../views/StudentLogin.vue';
import HelloWorld from '../views/HelloWorld.vue';
// StudentLogin.vueのログイン成功処理が/helloworldへ遷移し、HelloWorld.vueを表示する。
// HomeView.vueとAboutView.vueは用意されているが、現時点ではルートに登録されていない。
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
  }
];
const router = createRouter({
  history: createWebHistory(),
  routes
});
export default router;
