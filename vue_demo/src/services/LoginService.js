// StudentLogin.vueから呼び出す学生ログイン・登録APIの窓口。
// crs/src/.../StudentController.javaのPOST /api/login・/api/registerに対応する。
// http-common.jsのAxiosインスタンスは使わず、ここではaxiosと接続先を個別に指定している。
import axios from "axios";

const API_URL = "http://localhost:8080/api";

class LoginService {
    // StudentController.loginへstudentId/passwordをJSONで送り、成功時は学生IDと名前を受け取る。
    login(credentials) {
        return axios.post(`${API_URL}/login`, credentials);
    }

    // StudentController.registerStudentへ学生情報を送り、成否メッセージを受け取る。
    register(studentData) {
        return axios.post(`${API_URL}/register`, studentData);
    }
}

export default new LoginService();
