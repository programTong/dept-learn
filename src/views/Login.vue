<template>
  <div style="height: 100vh ;display: flex; background-image: linear-gradient(to bottom right, #aec4e4, #124895)">
    <el-card shadow="always" style="width: 400px; margin: auto">
      <div style="font-size: 20px; font-weight: bold; text-align: center; margin: 20px 0px;">欢迎登录</div>
      <el-form  :model="form" :rules="rules" ref="form" label-width="70px">
        <el-form-item label="账号" prop="empNo">
          <el-input v-model="form.empNo"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="empPassword">
          <el-input v-model="form.empPassword"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">登录</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { login } from "@/api/admin";
export default {
  name: "DeptLogin",
  data() {
    return {
      form: {},
      rules: {
        empNo: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        empPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    resetForm: function (){
      this.form = {}
    },
    submitForm: function (){
      let vm = this;
      console.log("登录")
      login(vm.form).then(data => {
        if (data != null && data != "") {
          let permissions = data.permissions.map(e=>{
            return e.permissionName
          });
          console.log(permissions);
          data.permissions = permissions;
          localStorage.removeItem("user");
          localStorage.setItem("user", JSON.stringify(data));
          // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
          vm.$router.push("/employee");
        } else {
          vm.$message({
            showClose: true,
            message: '用户名或密码错误',
            type: 'error'
          });
        }
      }).catch(err=>{
        vm.$message({
          showClose: true,
          message: '用户名或密码错误',
          type: 'error'
        });
        console.log(err);
      });
    }
  }

}
</script>

<style scoped>

</style>
