<template>
  <div>
    <div style="padding: 20px;">
      部门汇总
    </div>
    <div style="">
      <el-card>

<!--        上部的搜索框、按钮部分-->
        <div style="display: flex;
        justify-content: space-around;
        padding-right: 80px;
        ">
          <el-button v-if="checkPermission(['DEPT_DELETE'])"  @click="delBatch" type="primary"><i class="el-icon-delete-solid"></i> 批量删除</el-button>
          <el-button v-if="checkPermission(['DEPT_ADD'])" style="margin-left: 0" @click="add" type="primary"><i class="el-icon-plus"></i> 新增</el-button>
          <el-input v-model="query.departmentName" style="width: 120px" placeholder="部门名称"></el-input>
          <el-button @click="getData" type="primary"><i class="el-icon-search"></i> 搜索</el-button>
          <el-button @click="reset" style="margin-left: 0px" type="danger"><i class="el-icon-refresh"></i> 重置</el-button>
        </div>


        <!--        表格-->
        <div style="margin-top: 20px; width: 100%">
          <el-table
              border
              stripe
              :header-cell-style="{background:'#cadffa', color: '#887e7e'}"
              ref="multipleTable"
              :data="tableData"
              style="width: 100%;"
              @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="deptId" label="部门编号"></el-table-column>
            <el-table-column prop="deptName" label="部门名称"></el-table-column>
            <el-table-column prop="deptCreateTime" label="创建时间"></el-table-column>
            <el-table-column label="操作" width="200px" align="center">
              <template slot-scope="scope">
                <el-button v-if="checkPermission(['DEPT_EDIT'])" type="primary" @click="handleEdit(scope.row)" icon="el-icon-edit" circle></el-button>
                <el-popconfirm
                    v-if="checkPermission(['DEPT_DELETE'])"
                    title="确定删除吗？"
                    @confirm="del(scope.row)"
                    style="margin-left: 10px"
                >
                  <el-button slot="reference" type="danger" icon="el-icon-delete" circle></el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-card>
    </div>


    <!--    添加员工/编辑员工的对话框-->
    <div>
      <el-dialog title="新增部门" :visible.sync="dialogFormVisible">
        <el-form label-width="70px" ref="form" :model="form">
          <el-form-item prop="deptName" label="部门编号" >
            <el-input v-model="form.deptId" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="deptName" label="部门名称" >
            <el-input v-model="form.deptName" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
          </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import {getAll,remove} from '@/api/department';
import { checkPermission } from "@/utils/permission";
import {add, removeBatch, update} from "@/api/department";
export default {
  name: "DDepartment",
  data(){
    return {
      query: {
        departmentName: "",
      },
      tableData: [],
      dialogFormVisible: false,
      form: {},
      isAdd: false,
      multipleSelection: []
    }
  },
  created() {
    this.getData();
  },
  methods: {
    handleEdit(row){
      this.isAdd = false;
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true;
    },
    handleSelectionChange(val) {
      console.log(val);
      this.multipleSelection = val;
    },
    reset(){
      this.query = {}
      this.getData();
    },
    checkPermission,
    getData() {
      getAll(this.query.departmentName).then(res => {
        console.log(res);
        this.tableData = res;
      });
    },
    del(row){
      let vm = this;
      remove(row.deptId).then(res => {
        if (res) {
          vm.getData()
          vm.$message({
            message: '删除部门成功',
            type: "success"
          })
        } else {
          vm.$message({
            message: '删除部门失败',
            type: "error"
          })
        }
      });
    },
    delBatch() {
      let ids = this.multipleSelection.map(item => item.deptId)
      console.log(ids);
      let vm = this;
      removeBatch(ids).then(res => {
        if (res) {
          vm.$message({
            type: "success",
            message: "批量删除成功"
          })
          this.getData()
        } else {
          vm.$message({
            type: "error",
            message: "批量删除失败"
          })
        }
        console.log(res);
      })
    },
    save(){
      let vm = this;
      if (this.isAdd){
        add(this.form).then(data => {
          console.log(data);
          if (data) {
            this.dialogFormVisible = false;
            vm.getData()
            vm.$message({
              message: '新增部门成功',
              type: "success"
            })
          } else {
            vm.$message({
              message: '新增部门失败',
              type: "error"
            })
          }
        })
      } else {
        update(this.form).then(data => {
          if (data) {
            this.dialogFormVisible = false;
            vm.getData()
            vm.$message({
              message: '新增部门成功',
              type: "success"
            })
          } else {
            vm.$message({
              message: '新增部门失败',
              type: "error"
            })
          }
        })
      }
    },
    add(){
      this.form = {}
      this.dialogFormVisible = true;
      this.isAdd = true;
    },
  }
}
</script>

<style scoped>

</style>
