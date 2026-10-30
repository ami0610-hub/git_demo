import axios from "axios";

const API_URL = "http://localhost:8080/api";

class LoginService {
    login(credentials) {
        return axios.post(`${API_URL}/login`, credentials);
    }

     remove(credentials) {
    return axios.post(`${API_URL}/remove`, credentials);
  }


    register(studentData) {
        return axios.post(`${API_URL}/register`, studentData);
    }
}

export default new LoginService();