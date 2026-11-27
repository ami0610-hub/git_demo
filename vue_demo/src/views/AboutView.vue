<template>
  <!-- アプリ概要用の雛形ビュー。App.vueのrouter-viewに表示するにはrouter/index.jsへのルート登録が必要。 -->
  <div class="about">
    <h1>This is an about page</h1>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
