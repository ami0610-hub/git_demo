// Vueアプリケーションの起動点。App.vueとVue Routerを読み込み、#appへマウントする。
// router/index.jsが画面遷移を担い、各画面はApp.vue内のrouter-viewに表示される。
import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

createApp(App).use(router).mount("#app");
