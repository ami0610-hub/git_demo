<script setup>
// Welcomeコンテンツを表示するホーム画面。
// TheWelcome.vueを子として使う。現在のrouter/index.jsではこのビューはルートに割り当てられていない。
import TheWelcome from '../components/TheWelcome.vue'
</script>

<template>
  <main>
    <TheWelcome />
  </main>
</template>
