// Piniaで共有するカウンター状態を定義する。
// defineStoreがストアを作り、count・doubleCount・incrementを利用側コンポーネントへ公開する。
import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

// Piniaのカウンターストア。main.jsではPiniaを登録していないうえ、現在このストアを使う画面もない。
export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  // カウンター値を1増やす。
  function increment() {
    count.value++
  }

  return { count, doubleCount, increment }
})
