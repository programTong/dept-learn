<template>
    <el-container style="min-height: 100vh">
      <el-header height="70px">
        <Header/>
      </el-header>
      <el-container style="">
        <el-aside :width="asideWidth + 'px'">
          <Aside/>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>
import Aside from '@/components/Aside'
import Header from '@/components/Header'
import bus from '@/utils/bus'
export default {
  name: "DeptHome",
  components: {
    Aside,Header
  },
  data() {
    return {
      asideWidth: 200
    }
  },
  created() {
    bus.$on('collapseChange', msg => {
      console.log(msg);
      this.asideWidth = msg? 70 : 200;
    });
  }
}
</script>

<style scoped>
.el-header {
  padding: 0;
}

.el-aside{
  transition: width 0.7s;
  -webkit-transition: width 0.7s;
  -moz-transition: width 0.7s;
  -o-transition: width 0.7s;
  overflow-x: hidden;
}


</style>
