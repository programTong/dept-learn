<template>
  <div style="color: #fff;line-height: 70px;font-size: 25px; background-color: #242f42;height: 70px; display: flex">
    <span  style="margin-left: 25px; cursor: pointer " @click="collapseChange">
      <i v-if="!isCollapsed"  class="el-icon-s-fold"></i>
      <i v-if="isCollapsed"  class="el-icon-s-unfold"></i>
    </span>
    <span style="margin-left: 15px">员工信息管理系统</span>
    <span style="flex: 1; text-align: right; padding-right: 50px">
        <img style="vertical-align: middle ;width: 40px;height: 40px; border-radius: 50%; " src="../assets/logo.png" alt="">
        <el-dropdown>
          <span class="el-dropdown-link" style="color: #fff;cursor: pointer">
            {{username}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><router-link to="/repass">修改密码</router-link></el-dropdown-item>
            <el-dropdown-item><span  @click="$store.commit('logout')">退出登录</span></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
    </span>
  </div>
</template>

<script>
import bus from '@/utils/bus'
export default {
  name: "DHeader",
  data() {
    return {
      isCollapsed: false,
    }
  },
  computed: {
    username() {
      let username = JSON.parse(localStorage.getItem("user")).empName
      return username ? username: "??";
    }
  },
  methods: {
    collapseChange() {
      this.isCollapsed = !this.isCollapsed;
      bus.$emit('collapseChange', this.isCollapsed);
    },

  }
}
</script>

<style scoped>

</style>
