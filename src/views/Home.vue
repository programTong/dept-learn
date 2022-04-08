<template>
  <div class="wrapper">
    <Header/>
    <Aside/>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <div class="content">
            <router-view></router-view>
      </div>
    </div>
  </div>
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
      asideWidth: 200,
      collapse: false
    }
  },
  created() {
    bus.$on('collapseChange', msg => {
      this.collapse = msg;
      console.log(msg);
      this.asideWidth = msg? 70 : 200;
    });
  }
}
</script>

<style scoped>
.wrapper{
  width: 100%;
  height: 100vh;
}
.content-box{
  position: absolute;
  left: 250px;
  right: 0;
  top: 70px;
  bottom: 0;
  transition: left 0.3s ease-in-out;
}
.content{
  width: 100%;
  height: 100%;
  background-color: #f0f0f0;
  overflow-y: scroll;
  overflow-x: hidden;
}
.content-collapse {
  left: 64px;
}

</style>
