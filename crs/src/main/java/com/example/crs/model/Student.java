
package com.example.crs.model;

/**
 * 学生の登録・認証情報を表すデータクラス。
 * StudentControllerがJSONリクエストをこの型へ変換し、ログイン時の照合にも利用する。
 * JPAの@Entityではなく、現状はStudentController内のMapに保持される。
 */
public class Student {
    private String studentId;
    private String password;
    private String name;
    private String email;

    // Spring/JacksonがリクエストJSONからインスタンスを作るための引数なしコンストラクター。
    public Student() {
    }

    // Javaコードから学生情報をまとめて作る場合に使うコンストラクター。
    public Student(String studentId, String password, String name, String email) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // JacksonによるJSON変換とStudentControllerからの値参照・更新に使うアクセサー。
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
