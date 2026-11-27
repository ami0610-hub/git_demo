// API通信向けの共通Axios設定（接続先はcrsのSpring Boot API）。
// 現在のLoginService.jsはaxiosを直接使い、HelloWorld.vueもfetchを使うため、
// このインスタンスを使う呼び出し元はsrc内にまだない。今後API呼び出しを共通化するための設定。
import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-type": "application/json",
  },
});
