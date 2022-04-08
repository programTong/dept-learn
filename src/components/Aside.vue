<template>
  <div class="sidebar">
    <el-menu
        class="sidebar-el-menu"
              background-color="#324157"
              router
              text-color="#fff"
              :collapse="isCollapse">
      <div v-for="item in items" :key="item.index">
        <div v-if="item.role">
          <div v-if="item.subs">
            <el-submenu :index="item.index">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span slot="title">{{item.title}}</span>
              </template>
              <div v-for="subItem in item.subs" :key="subItem.index">
                <el-menu-item :index="subItem.index">
                  <i :class="subItem.icon"></i>
                  <span slot="title">{{subItem.title}}</span>
                </el-menu-item>
              </div>
            </el-submenu>
          </div>
          <div v-else>
            <el-menu-item :index="item.index">
              <i :class="item.icon"></i>
              <span slot="title">{{item.title}}</span>
            </el-menu-item>
          </div>
        </div>
      </div>
    </el-menu>
  </div>


</template>

<script>
import bus from '@/utils/bus'
import { checkPermission } from "@/utils/permission";
export default {
  name: "DAside",
  data() {
    return {
      isCollapse: false,
      items: [
        {
          icon: "el-icon-share",
          index: "employee",
          title: "员工汇总",
          role: checkPermission([
            "EMP_ADD",
            "EMP_EDIT",
            "EMP_DELETE",
            "EMP_SELECT"
          ])
        },
        {
          icon: "el-icon-tickets",
          index: "department",
          title: "部门汇总",
          role: checkPermission([
            "DEPT_ADD",
            "DEPT_EDIT",
            "DEPT_DELETE",
            "DEPT_SELECT"
          ])
        },

      ]
    }
  },
  created() {
    bus.$on('collapseChange', msg => {
      this.isCollapse = msg;
    })
  }
}
</script>

<style scoped>
.sidebar{
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar > ul {
  height: 100%;
}
</style>
